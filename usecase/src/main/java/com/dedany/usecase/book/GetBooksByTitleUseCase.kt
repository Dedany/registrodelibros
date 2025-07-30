package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository

class GetBooksByTitleUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(title: String): List<Book> = repository.getBooksByTitle(title)
}