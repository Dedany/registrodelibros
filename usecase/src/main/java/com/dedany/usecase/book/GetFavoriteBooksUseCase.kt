package com.dedany.usecase.book

import com.dedany.domain.entities.Book
import com.dedany.domain.repository.BookRepository

class GetFavoriteBooksUseCase (private val repository: BookRepository) {
    suspend operator fun invoke() : List<Book> = repository.getFavoriteBooks()
}