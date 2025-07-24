package com.dedany.domain.repository

import com.dedany.domain.entities.Author

interface AuthorRepository {
    suspend fun getAllAuthors(): List<Author>
    suspend fun getAuthorById(id: String): Author?
    suspend fun saveAuthor(author: Author)
    suspend fun getAuthorsByName(name: String): List<Author>
    suspend fun setAuthorFavorite(authorId: String, isFavorite: Boolean)
    suspend fun getFavoriteAuthors(): List<Author>
}