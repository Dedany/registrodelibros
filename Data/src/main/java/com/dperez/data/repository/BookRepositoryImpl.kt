package com.dperez.data.repository

import android.util.Log
import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book
import com.dedany.domain.repository.AuthorRepository
import com.dedany.domain.repository.BookAuthorRepository
import com.dedany.domain.repository.BookRepository
import com.dperez.data.datasource.local.localdatasource.BookLocalDataSource
import com.dperez.data.datasource.remote.dto.book.BookDetailDto
import com.dperez.data.datasource.remote.remotedatasource.BookRemoteDataSource
import com.dperez.data.mapper.toDomain
import com.dperez.data.mapper.toDbo
import com.dperez.data.mapper.toDboMerging
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookLocalDataSource: BookLocalDataSource,
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val authorRepository: AuthorRepository,
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

    override suspend fun setBookFavorite(bookId: String, isFavorite: Boolean) {
        bookLocalDataSource.setBookFavorite(bookId, isFavorite)
    }

    override suspend fun getFavoriteBooks(): List<Book> {
        return bookLocalDataSource.getFavoriteBooks().map { it.toDomain() }
    }

    override suspend fun getBookById(id: String): Book? {
        Log.d("BookRepoFlow", "[${Thread.currentThread().name}] getBookById - INICIO para ID: $id")
        val localBookDbo = bookLocalDataSource.getBookById(id)
        Log.d("TraceDebug", "Local BookDbo: $localBookDbo")

        if (localBookDbo?.description != null && localBookDbo.description.isNotEmpty()) {
            Log.d("BookRepoFlow", "[${Thread.currentThread().name}] Libro $id en caché con descripción.")
        }

        return try {
            val apiCompatibleId = id.substringAfterLast('/')
            val remoteBookDetailDto: BookDetailDto? = bookRemoteDataSource.getBookById(apiCompatibleId)
            Log.d("TraceDebug", "Remote BookDetailDto: $remoteBookDetailDto")

            if (remoteBookDetailDto != null) {
                val updatedBookDbo = remoteBookDetailDto.toDboMerging(localBookDbo)
                bookLocalDataSource.saveBook(updatedBookDbo)
                Log.i("BookRepoFlow", "[${Thread.currentThread().name}] GUARDADO BookDbo (ID: ${updatedBookDbo.id})")

                val authorWrappers = remoteBookDetailDto.authors
                if (!authorWrappers.isNullOrEmpty()) {
                    Log.d("BookRepoFlow", "[${Thread.currentThread().name}] Procesando ${authorWrappers.size} autores")
                    val crossRefsToInsert = mutableListOf<Pair<String, String>>()

                    for (wrapper in authorWrappers) {
                        val authorIdFromApi = wrapper.author.key
                        Log.d("BookRepoFlow", "[${Thread.currentThread().name}] Procesando AuthorKey: $authorIdFromApi")

                        val authorDomain: Author? = authorRepository.getAuthorById(authorIdFromApi)
                        Log.d("TraceDebug", "AuthorDomain obtenido: $authorDomain")

                        if (authorDomain != null) {
                            Log.i("BookRepoFlow", "[${Thread.currentThread().name}] Autor ${authorDomain.name} asegurado en BD.")
                            crossRefsToInsert.add(updatedBookDbo.id to authorDomain.id)
                        } else {
                            Log.w("BookRepoFlow", "[${Thread.currentThread().name}] No se pudo obtener/guardar el autor ID: $authorIdFromApi")
                        }
                    }

                    if (crossRefsToInsert.isNotEmpty()) {
                        Log.i("CrossRefDebug", "[${Thread.currentThread().name}] Insertando ${crossRefsToInsert.size} CrossRefs")
                        bookAuthorRepository.insertCrossRefs(crossRefsToInsert)

                        val savedCrossRefs = bookAuthorRepository.getCrossRefsForBook(updatedBookDbo.id)
                        Log.d("TraceDebug", "CrossRefs efectivamente guardadas: $savedCrossRefs")

                        for ((bookId, authorId) in savedCrossRefs) {
                            val author = authorRepository.getAuthorById(authorId)
                            Log.d("TraceDebug", "Autor recuperado para crossRef $bookId-$authorId: $author")
                        }
                    }
                } else {
                    Log.d("BookRepoFlow", "[${Thread.currentThread().name}] No hay autores en remoteBookDetailDto")
                }
                return updatedBookDbo.toDomain()
            } else {
                Log.w("BookRepoFlow", "[${Thread.currentThread().name}] remoteBookDetailDto fue null para $apiCompatibleId")
                return localBookDbo?.toDomain()
            }
        } catch (e: Exception) {
            Log.e("BookRepoFlow", "[${Thread.currentThread().name}] Error buscando libro $id: ${e.message}", e)
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
}
