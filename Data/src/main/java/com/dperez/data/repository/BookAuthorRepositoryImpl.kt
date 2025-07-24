package com.dperez.data.repository

import com.dedany.domain.entities.AuthorWithBooks
import com.dedany.domain.entities.BookWithAuthors
import com.dedany.domain.repository.BookAuthorRepository
import com.dperez.data.datasource.local.dao.BookAuthorDao
import com.dperez.data.datasource.local.dbo.BookAuthorCrossRef
import com.dperez.data.mapper.toDomain

class BookAuthorRepositoryImpl(
    private val dao: BookAuthorDao
): BookAuthorRepository {

    override suspend fun insertCrossRef(bookId: String, authorId: String) {
        dao.insertCrossRef(BookAuthorCrossRef(bookId, authorId))
    }

    override suspend fun insertCrossRefs(crossRefs: List<Pair<String, String>>) {
        dao.insertCrossRefs(
            crossRefs.map { (bookId, authorId) -> BookAuthorCrossRef(bookId, authorId) }
        )
    }

    override suspend fun getBookWithAuthors(bookId: String): BookWithAuthors {
        return dao.getBookWithAuthors(bookId).toDomain()
    }

    override suspend fun getAuthorWithBooks(authorId: String): AuthorWithBooks {
        return dao.getAuthorWithBooks(authorId).toDomain()
    }
}