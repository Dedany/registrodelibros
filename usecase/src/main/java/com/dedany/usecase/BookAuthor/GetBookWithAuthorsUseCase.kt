package com.dedany.usecase.BookAuthor

import com.dedany.domain.repository.BookAuthorRepository

class GetBookWithAuthorsUseCase(private val repository: BookAuthorRepository) {
    suspend operator fun invoke(bookId: String) = repository.getBookWithAuthors(bookId)
}