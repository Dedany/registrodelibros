package com.dperez.data.entity

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import com.dperez.data.datasource.local.BookDao
import com.dperez.data.toDomain
import com.dperez.data.toDbo

class BookRepositoryImpl(
    private val bookDao: BookDao
): BookRepository {
    override suspend fun getAllBooks(): List<Book> {
      return bookDao.getAllBooks().map { it.toDomain() }
    }

    override suspend fun getBookById(id: String): Book? {
        return bookDao.getBookById(id)?.toDomain()
    }

    override suspend fun saveBook(book: Book) {
        bookDao.saveBook(book.toDbo())
    }

    override suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book.toDbo())
    }

    override suspend fun searchBooksByAuthor(author: String): List<Book> {
        return bookDao.getBooksByAuthor(author).map { it.toDomain() }
    }

    override suspend fun searchBooksByTitle(title: String): List<Book> {
        return bookDao.getBooksByTitle(title).map { it.toDomain() }
    }
}