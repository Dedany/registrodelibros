package com.dedany.domain.entities

data class AuthorWithBooks(
    val author: Author,
    val books: List<Book>
)

data class BookWithAuthors(
    val book: Book,
    val authors: List<Author>
)