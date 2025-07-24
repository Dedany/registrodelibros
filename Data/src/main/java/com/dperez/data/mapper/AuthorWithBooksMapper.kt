package com.dperez.data.mapper

import com.dedany.domain.entities.AuthorWithBooks
import com.dedany.domain.entities.BookWithAuthors
import com.dperez.data.datasource.local.dbo.AuthorWithBooks as AuthorWithBooksDbo
import com.dperez.data.datasource.local.dbo.BookWithAuthors as BookWithAuthorsDbo


fun AuthorWithBooksDbo.toDomain(): AuthorWithBooks {
    return AuthorWithBooks(
        author = author.toDomain(),
        books = books.map { it.toDomain() }
    )
}

fun BookWithAuthorsDbo.toDomain(): BookWithAuthors {
    return BookWithAuthors(
        book = book.toDomain(),
        authors = authors.map { it.toDomain() }
    )
}