package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository
import javax.inject.Inject

class GetFavoriteBooksUseCase @Inject constructor  (private val repository: BookRepository) {
    suspend operator fun invoke() : List<Book> = repository.getFavoriteBooks()
}