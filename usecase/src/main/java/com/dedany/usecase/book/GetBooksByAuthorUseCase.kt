package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import javax.inject.Inject

class GetBooksByAuthorUseCase  @Inject constructor (private val repository: BookRepository) {
    suspend operator fun invoke(author: String): List<Book> = repository.getBooksByAuthor(author)

}