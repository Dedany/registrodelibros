package com.dperez.data.mapper

import com.dedany.domain.entities.Book
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.remote.dto.book.BookDetailDto
import com.dperez.data.datasource.remote.dto.book.BookDto
import com.dperez.data.datasource.remote.dto.book.SubjectDto

fun BookDbo.toDomain(): Book {
    return Book(
        id = this.id,
        title = this.title,
        coverId = this.coverId,
        publishYear = this.publishYear,
        description = this.description,
        subjects = this.subjects,
        rating = this.rating,
        isRead = this.isRead,
        isFavorite = this.isFavorite,
    )
}
fun SubjectDto.toDomain(genreSlug: String): List<Book> {
    return works.mapNotNull { work ->
        val id = work.key?.substringAfterLast('/') ?: return@mapNotNull null
        Book(
            id = id,
            title = work.title ?: "Sin título",
            coverId = work.coverId,
            publishYear = work.firstPublishYear,
            description = null,
            subjects = listOf(genreSlug),
            rating = 0,
            isRead = false,
            isFavorite = false
        )
    }
}

fun Book.toDbo(): BookDbo {
    return BookDbo(
        id = this.id,
        title = this.title,
        coverId = this.coverId,
        publishYear = this.publishYear,
        subjects = this.subjects,
        description = this.description,
        rating = this.rating,
        isRead = this.isRead,
        isFavorite = this.isFavorite
    )
}

fun BookDto.toDbo(): BookDbo {

    val cleanedId = this.key?.substringAfterLast('/') ?: ""
    return BookDbo(
        id = cleanedId,
        title = this.title,
        coverId = this.coverId,
        publishYear = this.publishYear,
        description = null,
        subjects = null,
        rating = 0,
        isRead = false,
        isFavorite = false,
    )
}


fun BookDetailDto.toDboMerging(existingDbo: BookDbo?): BookDbo {

    val cleanedId = this.key?.substringAfterLast('/') ?: ""
    val finalId = cleanedId

    return BookDbo(
       id = finalId,
        title = this.title ?: existingDbo?.title,
        coverId = this.covers?.firstOrNull() ?: existingDbo?.coverId,
        publishYear = existingDbo?.publishYear,
        description = this.description?.value ?: existingDbo?.description,
        subjects = this.subjects ?: existingDbo?.subjects,
        rating = existingDbo?.rating ?: 0,
        isRead = existingDbo?.isRead ?: false,
        isFavorite = existingDbo?.isFavorite ?: false
    )
}


