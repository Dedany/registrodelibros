package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository

class DeleteBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(book: Book) = repository.deleteBook(book)
}