package com.dperez.data.datasource.local.localdatasource

import com.dperez.data.datasource.local.dao.AuthorDao
import com.dperez.data.datasource.local.dbo.AuthorDbo
import javax.inject.Inject

class AuthorLocalDataSource @Inject constructor(
private val authorDao: AuthorDao
) {
    suspend fun getAllAuthors() = authorDao.getAllAuthors()
    suspend fun getAuthorById(id: String) = authorDao.getAuthorById(id)
    suspend fun saveAuthors(author: List<AuthorDbo>) = authorDao.saveAuthors(author)
    suspend fun saveAuthor(author: AuthorDbo) = authorDao.saveAuthor(author)
    suspend fun getAuthorsByName(name: String) = authorDao.getAuthorsByName(name)
    suspend fun setAuthorFavorite(authorId: String, isFavorite: Boolean) = authorDao.setAuthorFavorite(authorId, isFavorite)
    suspend fun getFavoriteAuthors() = authorDao.getFavoriteAuthors()

}