package com.dperez.data.repository

import com.dedany.domain.entities.Author
import com.dedany.domain.repository.AuthorRepository
import com.dperez.data.datasource.local.localdatasource.AuthorLocalDataSource
import com.dperez.data.datasource.remote.remotedatasource.AuthorRemoteDataSource
import com.dperez.data.mapper.toDbo
import com.dperez.data.mapper.toDomain
import javax.inject.Inject


class AuthorRepositoryImpl @Inject constructor(
    private val authorLocalDataSource: AuthorLocalDataSource,
    private val authorRemoteDataSource: AuthorRemoteDataSource
) : AuthorRepository {

    override suspend fun getAllAuthors(): List<Author> {
        val localAuthors = authorLocalDataSource.getAllAuthors()
        if (localAuthors.isNotEmpty()) {
            return localAuthors.map { it.toDomain() }
        }

        val responseDto = authorRemoteDataSource.searchAuthors("")
        val remoteAuthorsDto = responseDto.authors
        val remoteAuthorsDbo = remoteAuthorsDto.map { it.toDbo() }

        authorLocalDataSource.saveAuthors(remoteAuthorsDbo)

        return authorLocalDataSource.getAllAuthors().map { it.toDomain() }
    }

    override suspend fun getAuthorById(id: String): Author? {
        return authorLocalDataSource.getAuthorById(id)?.toDomain()
    }

    override suspend fun saveAuthor(author: Author) {
        authorLocalDataSource.saveAuthor(author.toDbo())
    }

    override suspend fun getAuthorsByName(name: String): List<Author> {
        return authorLocalDataSource.getAuthorsByName(name).map { it.toDomain() }
    }

    override suspend fun setAuthorFavorite(authorId: String, isFavorite: Boolean) {
        authorLocalDataSource.setAuthorFavorite(authorId, isFavorite)
    }

    override suspend fun getFavoriteAuthors(): List<Author> {
        return authorLocalDataSource.getFavoriteAuthors().map { it.toDomain() }

    }
}