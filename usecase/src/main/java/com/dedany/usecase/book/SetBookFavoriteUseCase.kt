package com.dedany.usecase.book

import com.dedany.domain.repository.BookRepository

class SetBookFavoriteUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(bookId: String, isFavorite: Boolean): Unit =
        repository.setBookFavorite(bookId, isFavorite)
}