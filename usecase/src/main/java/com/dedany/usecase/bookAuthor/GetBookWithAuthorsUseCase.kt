package com.dedany.usecase.bookAuthor

import com.dedany.domain.repository.BookAuthorRepository
import javax.inject.Inject

class GetBookWithAuthorsUseCase  @Inject constructor (private val repository: BookAuthorRepository) {
    suspend operator fun invoke(bookId: String) = repository.getBookWithAuthors(bookId)
}