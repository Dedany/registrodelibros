package com.dedany.usecase.bookAuthor

import com.dedany.domain.repository.BookAuthorRepository
import javax.inject.Inject

class InsertCrossRefUseCase  @Inject constructor (private val repository: BookAuthorRepository) {
    suspend operator fun invoke(bookId: String, authorId: String) = repository.insertCrossRef(bookId, authorId)
}