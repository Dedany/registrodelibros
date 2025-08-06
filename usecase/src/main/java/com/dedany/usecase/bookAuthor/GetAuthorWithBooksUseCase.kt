package com.dedany.usecase.bookAuthor

import com.dedany.domain.repository.BookAuthorRepository
import javax.inject.Inject


class GetAuthorWithBooksUseCase  @Inject constructor (private val repository: BookAuthorRepository) {
    suspend operator fun invoke(authorId: String) = repository.getAuthorWithBooks(authorId)
}
