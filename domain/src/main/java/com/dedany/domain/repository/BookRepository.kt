package com.dedany.domain.repository

import com.dedany.domain.entities.Book

interface BookRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun getBookById(id: String): Book?
    suspend fun saveBook(book: Book)
    suspend fun deleteBook(book: Book)
    suspend fun searchBooksByAuthor(author: String): List<Book>
    suspend fun searchBooksByTitle(title: String): List<Book>
}