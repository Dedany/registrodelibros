package com.dperez.data.mapper

import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dperez.data.datasource.local.dbo.AuthorDbo
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.remote.dto.author.AuthorDto
import com.dperez.data.datasource.remote.dto.author.BioField
import com.dperez.data.datasource.remote.dto.author.WorkDescriptionDto
import com.dperez.data.datasource.remote.dto.author.WorkDescriptionField
import com.dperez.data.datasource.remote.dto.author.WorkEntryDto

fun AuthorDbo.toDomain(): Author {
    return Author(
        id = id,
        name = name,
        birthDate = birthDate,
        topWork = topWork,
        workCount = workCount,
        alternateNames = alternateNames,
        topSubjects = topSubjects,
        bio = bio,
        isFavorite = isFavorite
    )
}

fun Author.toDbo(): AuthorDbo {
    return AuthorDbo(
        id = id,
        name = name,
        birthDate = birthDate,
        topWork = topWork,
        workCount = workCount,
        alternateNames = alternateNames,
        topSubjects = topSubjects,
        bio = bio,
        isFavorite = isFavorite
    )
}

fun AuthorDto.toDbo(): AuthorDbo {
    val bioText = when(val bioField = this.bio) {
        is BioField.BioString -> bioField.bioString
        is BioField.BioValue -> bioField.value
        else -> null
    }
    return AuthorDbo(
        id = this.key,
        name = this.name,
        birthDate = this.birthDate,
        topWork = this.topWork,
        workCount = this.workCount,
        alternateNames = this.alternateNames ?: emptyList(),
        topSubjects = this.topSubjects ?: emptyList(),
        bio = bioText,
        isFavorite = false
    )
}
fun WorkEntryDto.toDomain(): Book {
    val cleanWorkId = this.key?.substringAfterLast('/') ?: "" // Limpia el ID de la obra
    val coverId = this.covers?.firstOrNull()
    val descriptionText = when (val descField = this.description) { // Maneja si es objeto o string
        is WorkDescriptionField.DescriptionString -> descField.description
        is WorkDescriptionField.DescriptionValue -> descField.value
        // Si WorkEntryDto.description pudiera ser un String directamente (revisa la API)
        // is String -> desc
        else -> null
    }

    return Book(
        id = cleanWorkId,
        title = this.title ?: "Título desconocido",
        coverId = coverId,
        publishYear = this.firstPublishDate?.toIntOrNull(), // O como manejes el año
        description = descriptionText,
        subjects = null, // La API de obras por autor puede no dar subjects detallados
        rating = 0,      // No suele venir en esta llamada
        isRead = false,  // Estado local
        isFavorite = false // Estado local
        // Nota: Los autores de este libro NO se obtienen directamente de WorkEntryDto
        // de forma completa. Si quieres los autores de cada libro de la lista,
        // necesitarías hacer una llamada adicional por cada libro o tenerlos ya en caché.
    )
}

fun WorkEntryDto.toDbo(isFavoriteOverride: Boolean? = null, isReadOverride: Boolean? = null, ratingOverride: Int? = null): BookDbo {
    val cleanWorkId = this.key?.substringAfterLast('/') ?: ""
    val coverId = this.covers?.firstOrNull()
    val descriptionText = when (val desc = this.description) {
        is WorkDescriptionField.DescriptionString -> desc.description
        is WorkDescriptionField.DescriptionValue -> desc.value
        else -> null
    }

    return BookDbo(
        id = cleanWorkId,
        title = this.title ?: "Título desconocido",
        coverId = coverId,
        publishYear = this.firstPublishDate?.toIntOrNull(),
        description = descriptionText,
        rating = ratingOverride ?: 0,
        isRead = isReadOverride ?: false,
        isFavorite = isFavoriteOverride ?: false
    )
}