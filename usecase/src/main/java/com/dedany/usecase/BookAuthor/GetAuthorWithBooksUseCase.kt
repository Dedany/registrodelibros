package com.dedany.usecase.BookAuthor

import com.dedany.domain.repository.BookAuthorRepository

class GetAuthorWithBooksUseCase(private val repository: BookAuthorRepository) {
    suspend operator fun invoke(authorId: String) = repository.getAuthorWithBooks(authorId)
}
