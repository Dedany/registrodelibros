package com.dperez.data.mapper

import com.dedany.domain.entities.Book
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.remote.dto.book.BookDto

fun BookDbo.toDomain() : Book {
    return Book(
        id = this.id,
        title = this.title,
        author = this.author,
        coverId = this.coverId,
        publishYear = this.publishYear,
        rating = this.rating,
        isRead = this.isRead,
        isFavorite = this.isFavorite
    )
}

    fun Book.toDbo(): BookDbo {
        return BookDbo(
            id = this.id,
            title = this.title,
            author = this.author,
            coverId = this.coverId,
            publishYear = this.publishYear,
            rating = this.rating,
            isRead = this.isRead,
            isFavorite = this.isFavorite
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

