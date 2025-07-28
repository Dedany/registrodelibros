package com.dperez.data.datasource.local.localdatasource

import com.dperez.data.datasource.local.dao.BookDao
import com.dperez.data.datasource.local.dbo.BookDbo
import javax.inject.Inject

class BookLocalDataSource @Inject constructor(
    private val bookDao: BookDao
) {
    suspend fun getAllBooks() = bookDao.getAllBooks()
    suspend fun saveBook(book: BookDbo) = bookDao.saveBook(book)
    suspend fun setBookFavorite(bookId: String, isFavorite: Boolean) = bookDao.setBookFavorite(bookId, isFavorite)
    suspend fun getFavoriteBooks() = bookDao.getFavoriteBooks()
    suspend fun deleteBook(book: BookDbo) = bookDao.deleteBook(book)
    suspend fun getBookById(id: String) = bookDao.getBookById(id)
    suspend fun getBooksByAuthor(author: String) = bookDao.getBooksByAuthor(author)
    suspend fun getBooksByTitle(title: String) = bookDao.getBooksByTitle(title)

}