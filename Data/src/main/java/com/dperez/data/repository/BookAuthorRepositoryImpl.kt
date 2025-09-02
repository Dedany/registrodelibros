package com.dperez.data.repository

import android.util.Log
import com.dedany.domain.entities.AuthorWithBooks
import com.dedany.domain.entities.BookWithAuthors
import com.dedany.domain.repository.BookAuthorRepository
import com.dperez.data.datasource.local.dbo.BookAuthorCrossRef
import com.dperez.data.datasource.local.localdatasource.AuthorLocalDataSource
import com.dperez.data.datasource.local.localdatasource.BookAuthorLocalDataSource
import com.dperez.data.mapper.toDomain
import javax.inject.Inject

class BookAuthorRepositoryImpl @Inject constructor(
    private val bookAuthorLocalDataSource: BookAuthorLocalDataSource,
) : BookAuthorRepository {

    override suspend fun insertCrossRef(bookId: String, authorId: String) {
        bookAuthorLocalDataSource.insertCrossRef(BookAuthorCrossRef(bookId, authorId))
    }

    override suspend fun insertCrossRefs(crossRefs: List<Pair<String, String>>) {
        bookAuthorLocalDataSource.insertCrossRefs(
            crossRefs.map { (bookId, authorId) -> BookAuthorCrossRef(bookId, authorId) }
        )
    }

    override suspend fun getBookWithAuthors(bookId: String): BookWithAuthors?{
        Log.d("RepoDebug", "getBookWithAuthors - Requesting bookId: $bookId")
        val bookWithAuthorsDbo = bookAuthorLocalDataSource.getBookWithAuthors(bookId)
        Log.d("RepoDebug", "getBookWithAuthors - DBO from DataSource: $bookWithAuthorsDbo")
        if (bookWithAuthorsDbo != null) {
            Log.d(
                "RepoDebug",
                "DBO Authors: ${bookWithAuthorsDbo.authors.joinToString { it.toString() }}"
            )
        }
        val domainObject = bookWithAuthorsDbo?.toDomain()
        Log.d("RepoDebug", "getBookWithAuthors - Domain object: $domainObject")
        if (domainObject != null) {
            Log.d("RepoDebug", "Domain Authors: ${domainObject.authors.joinToString { it.name ?: "Nombre Nulo" }}")
        }
        return domainObject

    }

    override suspend fun getAuthorWithBooks(authorId: String): AuthorWithBooks? {
        val authorWithBooksDbo = bookAuthorLocalDataSource.getAuthorWithBooks(authorId)
        return authorWithBooksDbo?.toDomain()
    }
    override suspend fun getCrossRefsForBook(bookId: String): List<Pair<String, String>> {
        val crossRefsDbo = bookAuthorLocalDataSource.getCrossRefsByBookId(bookId)
        return crossRefsDbo.map { it.bookId to it.authorId }
    }

}