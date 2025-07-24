package com.dedany.domain.repository

import com.dedany.domain.entities.Book

interface BookRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun saveBook(book: Book)
    suspend fun setBookFavorite(bookId: String, isFavorite: Boolean)
    suspend fun getFavoriteBooks(): List<Book>
    suspend fun deleteBook(book: Book)
    suspend fun getBookById(id: String): Book?
    suspend fun getBooksByAuthor(author: String): List<Book>
    suspend fun getBooksByTitle(title: String): List<Book>
}