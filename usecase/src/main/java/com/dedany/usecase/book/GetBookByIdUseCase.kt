package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository

class GetBookByIdUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: String) : Book? = repository.getBookById(id)
}
