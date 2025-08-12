package com.dperez.data.repository

import android.util.Log // Asegúrate de tener este import
import com.dedany.domain.entities.Author
import com.dedany.domain.repository.AuthorRepository
import com.dperez.data.datasource.local.dbo.AuthorDbo
import com.dperez.data.datasource.local.localdatasource.AuthorLocalDataSource
import com.dperez.data.datasource.remote.dto.author.BioField // Necesitas este import si no está ya
import com.dperez.data.datasource.remote.remotedatasource.AuthorRemoteDataSource
import com.dperez.data.mapper.toDbo // Asegúrate que referencia a AuthorDto.toDbo y Author.toDbo
import com.dperez.data.mapper.toDomain // Asegúrate que referencia a AuthorDbo.toDomain
import com.dperez.data.datasource.remote.dto.author.AuthorDto // Importa tu AuthorDto
import javax.inject.Inject


class AuthorRepositoryImpl @Inject constructor(
    private val authorLocalDataSource: AuthorLocalDataSource,
    private val authorRemoteDataSource: AuthorRemoteDataSource
) : AuthorRepository {

    override suspend fun getAuthorById(id: String): Author? { // Author es tu entidad de dominio
        Log.d("AuthorRepo", "getAuthorById called for ID: $id")
        val localAuthorDbo: AuthorDbo? = authorLocalDataSource.getAuthorById(id)
        Log.d("AuthorRepo", "Local AuthorDbo for ID $id: $localAuthorDbo")
        Log.d("AuthorRepo", "Local AuthorDbo bio: ${localAuthorDbo?.bio}")

        // Condición para usar el local: si existe Y su biografía no es nula ni vacía
        if (localAuthorDbo?.bio != null && localAuthorDbo.bio.isNotEmpty()) {
            Log.i("AuthorRepo", "ID $id: Found Author in local cache WITH bio. Returning local.")
            return localAuthorDbo.toDomain() // Mapea el DBO local a la entidad de Dominio
        }

        Log.i("AuthorRepo", "ID $id: Author not in local cache OR bio is missing/empty. Fetching from remote.")

        // Asumo que tu 'id' de dominio ya es compatible con la API, si no, ajústalo.
        val remoteAuthorDto: AuthorDto? = authorRemoteDataSource.getAuthorById(id)
        Log.d("AuthorRepo", "ID $id: Remote AuthorDto from data source: $remoteAuthorDto")

        if (remoteAuthorDto == null) {
            Log.w("AuthorRepo", "ID $id: Failed to fetch AuthorDto from remote. Returning local (if any, even if incomplete).")
            // Si el remoto falla, devuelve el local aunque esté incompleto, si existe.
            // Si no existe local, entonces sí se devuelve null más adelante.
            return localAuthorDbo?.toDomain()
        }

        // --- Lógica de Fusión/Actualización ---
        val dboToSave: AuthorDbo

        // Extraer la biografía del DTO remoto
        val bioFromRemote = when (val bioField = remoteAuthorDto.bio) {
            is BioField.BioString -> bioField.bioString
            is BioField.BioValue -> bioField.value
            else -> null
        }
        Log.d("AuthorRepo", "ID $id: Bio extracted from remote DTO: $bioFromRemote")

        if (localAuthorDbo != null) {
            // El autor local existe, pero la bio faltaba (o estaba vacía).
            // Actualizamos el DBO local con la nueva bio (si existe) y mantenemos el resto de sus datos.
            Log.d("AuthorRepo", "ID $id: Local AuthorDbo exists. Merging with remote data.")
            dboToSave = localAuthorDbo.copy(
                // Actualiza campos que vienen del DTO remoto si son mejores o más completos
                name = remoteAuthorDto.name, // El nombre de la API suele ser el canónico
                birthDate = remoteAuthorDto.birthDate ?: localAuthorDbo.birthDate,
                topWork = remoteAuthorDto.topWork ?: localAuthorDbo.topWork,
                workCount = remoteAuthorDto.workCount ?: localAuthorDbo.workCount,
                alternateNames = remoteAuthorDto.alternateNames ?: localAuthorDbo.alternateNames,
                topSubjects = remoteAuthorDto.topSubjects ?: localAuthorDbo.topSubjects,
                bio = bioFromRemote ?: localAuthorDbo.bio, // Prioriza la bio remota si existe, sino usa la local (que sería null/empty aquí)
                isFavorite = localAuthorDbo.isFavorite // ¡MUY IMPORTANTE! Conservar el estado de favorito local
            )
            Log.d("AuthorRepo", "ID $id: Merged AuthorDbo: $dboToSave")
        } else {
            // El autor no estaba en la base de datos local en absoluto.
            // Mapea el DTO remoto completamente a un nuevo DBO.
            // Tu mapeador AuthorDto.toDbo() ya establece isFavorite = false por defecto.
            Log.d("AuthorRepo", "ID $id: Author not in local cache. Mapping remote DTO to new DBO.")
            dboToSave = remoteAuthorDto.toDbo() // Usa tu mapeador AuthorDto.toDbo()
            Log.d("AuthorRepo", "ID $id: New AuthorDbo from remote: $dboToSave")
        }

        Log.i("AuthorRepo", "ID $id: Saving AuthorDbo to local: $dboToSave")
        authorLocalDataSource.saveAuthor(dboToSave) // Guarda el DBO actualizado/nuevo
        return dboToSave.toDomain() // Mapea el DBO guardado a la entidad de Dominio
    }

    // Aquí van tus otros métodos como saveAuthor, getAuthorsByName, setAuthorFavorite, getFavoriteAuthors
    // Asegúrate de que no se vean afectados negativamente o ajústalos si es necesario,
    // aunque esta lógica de getAuthorById es bastante autocontenida.

    override suspend fun saveAuthor(author: Author) {
        authorLocalDataSource.saveAuthor(author.toDbo())
    }

    override suspend fun getAuthorsByName(name: String): List<Author> {
        // Esta lógica parece estar bien. Si los autores de la búsqueda no tienen bio,
        // al ir a getAuthorById se obtendrá la bio.
        val localAuthors = authorLocalDataSource.getAuthorsByName(name)
        if (localAuthors.isNotEmpty()) {
            return localAuthors.map { it.toDomain() }
        }

        val responseDto = authorRemoteDataSource.searchAuthors(name)

        if (responseDto?.authors != null && responseDto.authors.isNotEmpty()) {
            val remoteAuthorsDto = responseDto.authors
            // remoteAuthorsDto.mapNotNull { it.toDbo() } usará tu AuthorDto.toDbo()
            // que pondrá bio = null si la API de búsqueda no lo da, lo cual está bien.
            val remoteAuthorsDbo = remoteAuthorsDto.mapNotNull { it.toDbo() }
            if (remoteAuthorsDbo.isNotEmpty()) {
                authorLocalDataSource.saveAuthors(remoteAuthorsDbo)
                return remoteAuthorsDbo.map { it.toDomain() }
            }
        }
        return emptyList()
    }

    override suspend fun setAuthorFavorite(authorId: String, isFavorite: Boolean) {
        authorLocalDataSource.setAuthorFavorite(authorId, isFavorite)
    }

    override suspend fun getFavoriteAuthors(): List<Author> {
        return authorLocalDataSource.getFavoriteAuthors().map { it.toDomain() }
    }
}
