package com.dedany.usecase.book

import com.dedany.domain.repository.BookRepository
import com.dedany.domain.entities.Book

class GetAllBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(): List<Book> {
        return repository.getAllBooks()
    }
}