package com.dedany.usecase.BookAuthor

import com.dedany.domain.repository.BookAuthorRepository

class InsertCrossRefUseCase(private val repository: BookAuthorRepository) {
    suspend operator fun invoke(bookId: String, authorId: String) = repository.insertCrossRef(bookId, authorId)
}