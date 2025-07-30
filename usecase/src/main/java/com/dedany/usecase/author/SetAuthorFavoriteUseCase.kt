package com.dedany.usecase.author

import com.dedany.domain.repository.AuthorRepository

class SetAuthorFavoriteUseCase(private val repository: AuthorRepository) {
    suspend operator fun invoke(authorId: String, isFavorite: Boolean): Unit =
        repository.setAuthorFavorite(authorId, isFavorite)
}