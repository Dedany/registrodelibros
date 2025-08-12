package com.dperez.data.datasource.local.dbo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BookDetailDbo(
    @Embedded val book: BookDbo,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = BookAuthorCrossRef::class,
            parentColumn = "bookId",
            entityColumn = "authorId"
        )
    )
    val authors: List<AuthorDbo>
)