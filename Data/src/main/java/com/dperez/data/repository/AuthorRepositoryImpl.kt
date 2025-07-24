package com.dperez.data.repository

import com.dedany.domain.entities.Author
import com.dedany.domain.repository.AuthorRepository
import com.dperez.data.datasource.local.dao.AuthorDao
import com.dperez.data.mapper.toDbo
import com.dperez.data.mapper.toDomain


class AuthorRepositoryImpl(
    private val authorDao: AuthorDao
) : AuthorRepository {
    override suspend fun getAllAuthors(): List<Author> {
        return authorDao.getAllAuthors().map { it.toDomain() }
    }

    override suspend fun getAuthorById(id: String): Author? {
        return authorDao.getAuthorById(id)?.toDomain()
    }

    override suspend fun saveAuthor(author: Author) {
        authorDao.saveAuthor(author.toDbo())
    }

    override suspend fun getAuthorsByName(name: String): List<Author> {
        return authorDao.getAuthorsByName(name).map { it.toDomain() }
    }

    override suspend fun setAuthorFavorite(authorId: String, isFavorite: Boolean) {
        authorDao.setAuthorFavorite(authorId, isFavorite)
    }

    override suspend fun getFavoriteAuthors(): List<Author> {
        return authorDao.getFavoriteAuthors().map { it.toDomain() }

    }
}