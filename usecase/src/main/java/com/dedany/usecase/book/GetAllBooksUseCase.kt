package com.dedany.usecase.book

import com.dedany.domain.repository.BookRepository
import com.dedany.domain.entities.Book
import javax.inject.Inject

class GetAllBooksUseCase @Inject constructor (private val repository: BookRepository) {
    suspend operator fun invoke(): List<Book> {
        return repository.getAllBooks()
    }
}