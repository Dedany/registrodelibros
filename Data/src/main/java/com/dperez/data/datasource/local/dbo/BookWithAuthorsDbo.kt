package com.dperez.data.datasource.local.dbo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["bookId", "authorId"])
data class BookAuthorCrossRef(
    val bookId: String,
    val authorId: String
)


data class BookWithAuthors(
    @Embedded val book: BookDbo,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value =  BookAuthorCrossRef::class,
            parentColumn = "bookId",
            entityColumn = "authorId")
    )

    val authors: List<AuthorDbo>
)

data class AuthorWithBooks(
    @Embedded val author: AuthorDbo,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = BookAuthorCrossRef::class,
            parentColumn = "authorId",
            entityColumn = "bookId")
    )

    val books: List<BookDbo>
)
