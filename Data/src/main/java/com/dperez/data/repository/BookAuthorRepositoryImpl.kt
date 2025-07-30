package com.dperez.data.repository

import com.dedany.domain.entities.AuthorWithBooks
import com.dedany.domain.entities.BookWithAuthors
import com.dedany.domain.repository.BookAuthorRepository
import com.dperez.data.datasource.local.dbo.BookAuthorCrossRef
import com.dperez.data.datasource.local.localdatasource.BookAuthorLocalDataSource
import com.dperez.data.mapper.toDomain
import javax.inject.Inject

class BookAuthorRepositoryImpl @Inject constructor(
    private val bookAuthorLocalDataSource: BookAuthorLocalDataSource
): BookAuthorRepository {

    override suspend fun insertCrossRef(bookId: String, authorId: String) {
        bookAuthorLocalDataSource.insertCrossRef(BookAuthorCrossRef(bookId, authorId))
    }

    override suspend fun insertCrossRefs(crossRefs: List<Pair<String, String>>) {
        bookAuthorLocalDataSource.insertCrossRefs(
            crossRefs.map { (bookId, authorId) -> BookAuthorCrossRef(bookId, authorId) }
        )
    }

    override suspend fun getBookWithAuthors(bookId: String): BookWithAuthors {
        return bookAuthorLocalDataSource.getBookWithAuthors(bookId).toDomain()
    }

    override suspend fun getAuthorWithBooks(authorId: String): AuthorWithBooks {
        return bookAuthorLocalDataSource.getAuthorWithBooks(authorId).toDomain()
    }
}