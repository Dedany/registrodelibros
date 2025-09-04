package com.dedany.usecase.author

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import javax.inject.Inject

class GetBooksByAuthorUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(authorId: String): List<Book> {
        if (authorId.isBlank()) return emptyList()
        return try {
            bookRepository.getBooksByAuthorId(authorId)
        } catch (e: Exception) {
            emptyList()
        }
    }
}