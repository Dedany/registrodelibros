package com.dperez.data.repository

import android.util.Log
import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dedany.domain.repository.AuthorRepository
import com.dedany.domain.repository.BookAuthorRepository
import com.dedany.domain.repository.BookRepository
import com.dperez.data.datasource.local.localdatasource.BookAuthorLocalDataSource
import com.dperez.data.datasource.local.localdatasource.BookLocalDataSource
import com.dperez.data.datasource.remote.dto.book.BookDetailDto
import com.dperez.data.datasource.remote.dto.book.SubjectDto
import com.dperez.data.datasource.remote.remotedatasource.AuthorRemoteDataSource
import com.dperez.data.datasource.remote.remotedatasource.BookRemoteDataSource
import com.dperez.data.mapper.toDomain
import com.dperez.data.mapper.toDbo
import com.dperez.data.mapper.toDboMerging
import javax.inject.Inject
import kotlin.text.isEmpty
import kotlin.text.isNullOrBlank
import kotlin.text.mapNotNull
import kotlin.text.substringAfterLast

class BookRepositoryImpl @Inject constructor(
    private val bookLocalDataSource: BookLocalDataSource,
    private val bookAuthorLocalDataSource: BookAuthorLocalDataSource,
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val authorRepository: AuthorRepository,
    private val authorRemoteDataSource: AuthorRemoteDataSource,
    private val bookAuthorRepository: BookAuthorRepository
) : BookRepository {

    override suspend fun getAllBooks(): List<Book> {
        val localBooks = bookLocalDataSource.getAllBooks()
        if (localBooks.isNotEmpty()) return localBooks.map { it.toDomain() }

        return try {
            val responseDto = bookRemoteDataSource.searchBooksByTitle("a")
            val remoteBooksDto = responseDto.books.orEmpty()
            if (remoteBooksDto.isEmpty()) return emptyList()

            val remoteBooksDbo = remoteBooksDto.map { it.toDbo() }
            bookLocalDataSource.saveBooks(remoteBooksDbo)

            bookLocalDataSource.getAllBooks().map { it.toDomain() }
        } catch (e: Exception) {
            Log.e("BookRepoFlow", "Error en getAllBooks: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun saveBook(book: Book) {
        bookLocalDataSource.saveBook(book.toDbo())
    }

    override suspend fun  setBookFavorite(bookId: String, isFavorite: Boolean) {
        bookLocalDataSource.setBookFavorite(bookId, isFavorite)
    }

    override suspend fun getFavoriteBooks(): List<Book> {
        return bookLocalDataSource.getFavoriteBooks().map { it.toDomain() }
    }


    override suspend fun getBooksByGenre(genre: String): List<Book> {
        val slug = genre.trim().lowercase().replace(" ", "_")
        val local = bookLocalDataSource.getBooksByGenre(slug)
        if (local.isNotEmpty()) {
            Log.d("BookRepo-Debug", "✅ Encontrados ${local.size} libros para '$slug' en caché local.")
            return local.map { it.toDomain() }
        }

        Log.d("BookRepo-Debug", "ℹ️ No hay caché para '$slug'. Consultando API. URL: https://openlibrary.org/subjects/$slug.json")
        return try {
            val response = bookRemoteDataSource.searchBooksBySubject(slug)

            if (response.isSuccessful) {
                val dto = response.body() // Usamos '?' en lugar de '!!' para evitar el crash
                if (dto != null) {
                    // ÉXITO REAL
                    Log.i("BookRepo-Debug", "✅ Respuesta OK para '$slug'. Obras encontradas: ${dto.works.size}. Primer título: ${dto.works.firstOrNull()?.title}")
                    val books = dto.toDomain(slug)
                    if (books.isNotEmpty()) {
                        bookLocalDataSource.saveBooks(books.map { it.toDbo() })
                    }
                    books
                } else {
                    // ERROR SILENCIOSO DE GSON
                    Log.e("BookRepo-Debug", "❌ ERROR: Respuesta OK (código 200), PERO EL CUERPO ES NULO. Esto casi siempre es un problema de deserialización con GSON. Revisa que tu clase SubjectDto coincida con la respuesta JSON de la API.")
                    emptyList()
                }
            } else {
                // ERROR HTTP (404, 500, etc.)
                val errorBody = response.errorBody()?.string() ?: "Sin cuerpo de error"
                Log.e("BookRepo-Debug", "❌ ERROR HTTP para '$slug'. Código: ${response.code()}. Mensaje: ${response.message()}. Cuerpo del error: $errorBody")
                emptyList()
            }

        } catch (e: Exception) {
            // ERROR DE RED, TIMEOUT, ETC.
            Log.e("BookRepo-Debug", "❌ EXCEPCIÓN CATASTRÓFICA al obtener libros para '$slug': ${e.javaClass.simpleName} - ${e.message}", e)
            emptyList()
        }
    }





    override suspend fun getBookDetail(id: String): Book {
        val localBook = bookLocalDataSource.getBookById(id)
        val remoteDetail = bookRemoteDataSource.getBookById(id)

        val mergedDbo = remoteDetail.toDboMerging(localBook)
        bookLocalDataSource.saveBook(mergedDbo)

        return mergedDbo.toDomain()
    }



    override suspend fun getBookById(id: String): Book? {
        Log.d("BookRepoFlow", "[${Thread.currentThread().name}] getBookById - INICIO para ID: $id")
        val localBookDbo = bookLocalDataSource.getBookById(id)
        Log.d("TraceDebug", "Local BookDbo: $localBookDbo")

        if (localBookDbo?.description != null && localBookDbo.description.isNotEmpty()) {
            Log.d(
                "BookRepoFlow",
                "[${Thread.currentThread().name}] Libro $id en caché con descripción."
            )
        }

        return try {
            val apiCompatibleId = id.substringAfterLast('/')
            val remoteBookDetailDto: BookDetailDto? =
                bookRemoteDataSource.getBookById(apiCompatibleId)
            Log.d("TraceDebug", "Remote BookDetailDto: $remoteBookDetailDto")

            if (remoteBookDetailDto != null) {
                val updatedBookDbo = remoteBookDetailDto.toDboMerging(localBookDbo)
                bookLocalDataSource.saveBook(updatedBookDbo)
                Log.i(
                    "BookRepoFlow",
                    "[${Thread.currentThread().name}] GUARDADO BookDbo (ID: ${updatedBookDbo.id})"
                )

                val authorWrappers = remoteBookDetailDto.authors
                if (!authorWrappers.isNullOrEmpty()) {
                    Log.d(
                        "BookRepoFlow",
                        "[${Thread.currentThread().name}] Procesando ${authorWrappers.size} autores"
                    )
                    val crossRefsToInsert = mutableListOf<Pair<String, String>>()

                    for (wrapper in authorWrappers) {
                        val authorIdFromApi = wrapper.author.key
                        Log.d(
                            "BookRepoFlow",
                            "[${Thread.currentThread().name}] Procesando AuthorKey: $authorIdFromApi"
                        )

                        val authorDomain: Author? = authorRepository.getAuthorById(authorIdFromApi)
                        Log.d("TraceDebug", "AuthorDomain obtenido: $authorDomain")

                        if (authorDomain != null) {
                            Log.i(
                                "BookRepoFlow",
                                "[${Thread.currentThread().name}] Autor ${authorDomain.name} asegurado en BD."
                            )
                            crossRefsToInsert.add(updatedBookDbo.id to authorDomain.id)
                        } else {
                            Log.w(
                                "BookRepoFlow",
                                "[${Thread.currentThread().name}] No se pudo obtener/guardar el autor ID: $authorIdFromApi"
                            )
                        }
                    }

                    if (crossRefsToInsert.isNotEmpty()) {
                        Log.i(
                            "CrossRefDebug",
                            "[${Thread.currentThread().name}] Insertando ${crossRefsToInsert.size} CrossRefs"
                        )
                        bookAuthorRepository.insertCrossRefs(crossRefsToInsert)

                        val savedCrossRefs =
                            bookAuthorRepository.getCrossRefsForBook(updatedBookDbo.id)
                        Log.d("TraceDebug", "CrossRefs efectivamente guardadas: $savedCrossRefs")

                        for ((bookId, authorId) in savedCrossRefs) {
                            val author = authorRepository.getAuthorById(authorId)
                            Log.d(
                                "TraceDebug",
                                "Autor recuperado para crossRef $bookId-$authorId: $author"
                            )
                        }
                    }
                } else {
                    Log.d(
                        "BookRepoFlow",
                        "[${Thread.currentThread().name}] No hay autores en remoteBookDetailDto"
                    )
                }
                return updatedBookDbo.toDomain()
            } else {
                Log.w(
                    "BookRepoFlow",
                    "[${Thread.currentThread().name}] remoteBookDetailDto fue null para $apiCompatibleId"
                )
                return localBookDbo?.toDomain()
            }
        } catch (e: Exception) {
            Log.e(
                "BookRepoFlow",
                "[${Thread.currentThread().name}] Error buscando libro $id: ${e.message}",
                e
            )
            return localBookDbo?.toDomain()
        }
    }

    override suspend fun getBooksByAuthor(author: String): List<Book> {
        return bookLocalDataSource.getBooksByAuthor(author).map { it.toDomain() }
    }

    override suspend fun deleteBook(book: Book) {
        bookLocalDataSource.deleteBook(book.toDbo())
    }

    override suspend fun getBooksByTitle(title: String): List<Book> {
        val trimmedTitle = title.trim()
        if (trimmedTitle.isEmpty()) return emptyList()

        val localBooks = bookLocalDataSource.getBooksByTitle(trimmedTitle)
        if (localBooks.isNotEmpty()) return localBooks.map { it.toDomain() }

        val responseDto = bookRemoteDataSource.searchBooksByTitle(trimmedTitle)
        val remoteBooksDto = responseDto.books
        val remoteBooksDbo = remoteBooksDto.map { it.toDbo() }

        bookLocalDataSource.saveBooks(remoteBooksDbo)
        return bookLocalDataSource.getBooksByTitle(trimmedTitle).map { it.toDomain() }
    }
    private fun filterAndCleanBooks(books: List<Book>): List<Book> {
        return books
            .map { book ->
                // Normaliza título a string no-null y trim
                val cleanTitle = book.title?.trim().orEmpty()
                book.copy(title = cleanTitle)
            }
            .distinctBy { it.title?.lowercase() }
            .filter { book ->
                // Aquí title ya es no-null
                val title = book.title.orEmpty().lowercase()
                // Filtra fuera ediciones limitadas o boxed set
                !title.contains("limited edition") &&
                        !title.contains("boxed set")
            }
            // Ajusta o quita el límite según prefieras
            .take(50)
    }


    //llamar a Local por idAuthor si es nula o vacio llamar a remote y si no llamo a local
    override suspend fun getBooksByAuthorId(authorId: String): List<Book> {
        Log.d("BookRepo", "getBooksByAuthorId - Solicitando libros para autor ID: $authorId")

        // 1. Consultar en local mediante crossRef
        val localBooks = bookAuthorLocalDataSource.getBooksByAuthorId(authorId)
        if (localBooks.isNotEmpty()) {
            //POLÍTICA DE CACHÉ 10 días
            Log.d("BookRepo", "getBooksByAuthorId - Encontrados ${localBooks.size} libros en local")
            val domainLocal = localBooks.map { it.toDomain() }
            return filterAndCleanBooks(domainLocal)
        }else {
            Log.d("BookRepo", "getBooksByAuthorId - No hay libros en local, consultando remoto...")
            val worksResponseDto = authorRemoteDataSource.getWorksByAuthorId(authorId)
            if (worksResponseDto?.entries.isNullOrEmpty()) {
                Log.d(
                    "BookRepo",
                    "getBooksByAuthorId - No se encontraron obras remotas para autor $authorId"
                )
                return emptyList()
            }

            val books = mutableListOf<Book>()

            for (workEntryDto in worksResponseDto.entries.orEmpty()) {
                val cleanWorkId = workEntryDto.key?.substringAfterLast('/') ?: continue

                // Si existía en local (favoritos, leídos, rating)
                val existingLocalBookDbo = bookLocalDataSource.getBookById(cleanWorkId)

                // Mapear y guardar
                val bookDboToSave = workEntryDto.toDbo(
                    isFavoriteOverride = existingLocalBookDbo?.isFavorite,
                    isReadOverride = existingLocalBookDbo?.isRead,
                    ratingOverride = existingLocalBookDbo?.rating
                )
                bookLocalDataSource.saveBook(bookDboToSave)

                // Guardar relación autor–libro
                bookAuthorRepository.insertCrossRefs(listOf(cleanWorkId to authorId))

                books.add(bookDboToSave.toDomain())
            }

            Log.d(
                "BookRepo",
                "getBooksByAuthorId - Guardados ${books.size} libros en local desde remoto"
            )
            return filterAndCleanBooks(books)
        }

    }

}