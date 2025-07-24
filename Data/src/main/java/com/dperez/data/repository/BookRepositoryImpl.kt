package com.dperez.data.repository

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import com.dperez.data.datasource.local.dao.BookDao
import com.dperez.data.mapper.toDomain
import com.dperez.data.mapper.toDbo

class BookRepositoryImpl(
    private val bookDao: BookDao
) : BookRepository {
    override suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks().map { it.toDomain() }
    }

    override suspend fun saveBook(book: Book) {
        bookDao.saveBook(book.toDbo())
    }

    override suspend fun setBookFavorite(bookId: String, isFavorite: Boolean) {
        bookDao.setBookFavorite(bookId, isFavorite)
    }

    override suspend fun getFavoriteBooks(): List<Book> {
        return bookDao.getFavoriteBooks().map { it.toDomain() }
    }


    override suspend fun getBookById(id: String): Book? {
        return bookDao.getBookById(id)?.toDomain()
    }

    override suspend fun getBooksByAuthor(author: String): List<Book> {
        return bookDao.getBooksByAuthor(author).map { it.toDomain() }
    }

    override suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book.toDbo())
    }


    override suspend fun getBooksByTitle(title: String): List<Book> {
        return bookDao.getBooksByTitle(title).map { it.toDomain() }
    }
}
