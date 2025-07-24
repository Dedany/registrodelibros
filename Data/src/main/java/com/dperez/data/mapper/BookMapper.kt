package com.dperez.data.mapper

import com.dedany.domain.entities.Book
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.remote.dto.book.BookDto

fun BookDbo.toDomain() : Book {
    return Book(
        id = this.id,
        title = this.title ?: "Sin título",
        author = this.author?.joinToString(", ") ?: "Autor desconocido",
        coverUrl = "https://covers.openlibrary.org/b/id/${this.coverId}-M.jpg?default=false",
        publishYear = this.publishYear,
        rating = this.rating,
        isRead = this.isRead

    )
}

    fun Book.toDbo(): BookDbo {
        return BookDbo(
            id = this.id,
            title = this.title,
            author = this.author
                .split(",")
                .map { it.trim() },
            coverId = this.coverUrl?.split("/")?.last()?.split("-")?.first()?.toInt(),
            publishYear = null,
            rating = this.rating,
            isRead = this.isRead

        )
    }

fun BookDto.toDbo(): BookDbo {
    return BookDbo(
        id = this.key ?: "",
        title = this.title,
        author = this.authorName,
        coverId = this.coverId,
        publishYear = this.publishYear,
    )
}

