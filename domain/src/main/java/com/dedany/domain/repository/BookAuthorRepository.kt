package com.dedany.domain.repository

import com.dedany.domain.entities.AuthorWithBooks
import com.dedany.domain.entities.BookWithAuthors

interface BookAuthorRepository {

    suspend fun insertCrossRef(bookId: String, authorId: String)
    suspend fun insertCrossRefs(crossRefs: List<Pair<String, String>>)

    suspend fun getBookWithAuthors(bookId: String): BookWithAuthors
    suspend fun getAuthorWithBooks(authorId: String): AuthorWithBooks

}