package com.dperez.data.repository

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import com.dperez.data.datasource.local.dao.BookDao
import com.dperez.data.datasource.local.localdatasource.BookLocalDataSource
import com.dperez.data.datasource.remote.remotedatasource.BookRemoteDataSource
import com.dperez.data.mapper.toDomain
import com.dperez.data.mapper.toDbo
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookLocalDataSource: BookLocalDataSource,
    private val bookRemoteDataSource: BookRemoteDataSource,
) : BookRepository {

    override suspend fun getAllBooks(): List<Book> {
        val localBooks = bookLocalDataSource.getAllBooks()
        if (localBooks.isNotEmpty()) {
            return localBooks.map { it.toDomain() }
        }

        return try {
            val responseDto =
                bookRemoteDataSource.searchBooksByTitle("a")

            val remoteBooksDto = responseDto.books.orEmpty()
            if (remoteBooksDto.isEmpty()) return emptyList()

            val remoteBooksDbo = remoteBooksDto.map { it.toDbo() }
            bookLocalDataSource.saveBooks(remoteBooksDbo)

            bookLocalDataSource.getAllBooks().map { it.toDomain() }

        } catch (e: Exception) {

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
        return bookLocalDataSource.getBookById(id)?.toDomain()
    }

    override suspend fun getBooksByAuthor(author: String): List<Book> {
        return bookLocalDataSource.getBooksByAuthor(author).map { it.toDomain() }
    }

    override suspend fun deleteBook(book: Book) {
        bookLocalDataSource.deleteBook(book.toDbo())
    }


    override suspend fun getBooksByTitle(title: String): List<Book> {
        val trimmedTitle = title.trim()

        if (trimmedTitle.isEmpty()) {
            return emptyList()
        }

        val localBooks = bookLocalDataSource.getBooksByTitle(trimmedTitle)
        if (localBooks.isNotEmpty()) {
            return localBooks.map { it.toDomain() }
        }

        val responseDto = bookRemoteDataSource.searchBooksByTitle(trimmedTitle)
        val remoteBooksDto = responseDto.books
        val remoteBooksDbo = remoteBooksDto.map { it.toDbo() }

        bookLocalDataSource.saveBooks(remoteBooksDbo)

        return bookLocalDataSource.getBooksByTitle(trimmedTitle).map { it.toDomain() }
    }
}
