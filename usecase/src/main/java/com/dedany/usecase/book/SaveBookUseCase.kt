package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository

    class SaveBookUseCase(private val repository: BookRepository) {
        suspend operator fun invoke(book: Book): Unit = repository.saveBook(book)
    }
