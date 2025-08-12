package com.dperez.data.mapper

import com.dedany.domain.entities.Book
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.remote.dto.book.BookDetailDto
import com.dperez.data.datasource.remote.dto.book.BookDto

fun BookDbo.toDomain(): Book {
    return Book(
        id = this.id,
        title = this.title,
        author = this.author,
        coverId = this.coverId,
        publishYear = this.publishYear,
        description = this.description,
        subjectPlaces = this.subjectPlaces,
        subjectPeople = this.subjectPeople,
        subjectTimes = this.subjectTimes,
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
        description = this.description,
        subjectPlaces = this.subjectPlaces,
        subjectPeople = this.subjectPeople,
        subjectTimes = this.subjectTimes,
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
        description = null,
        subjectPlaces = null,
        subjectPeople = null,
        subjectTimes = null,
        rating = 0,
        isRead = false,
        isFavorite = false
    )
}

fun BookDetailDto.toDbo(): BookDbo {
    return BookDbo(
        id = this.key ?: "",
        title = this.title,
        author = this.authors?.map { it.author.key } ?: emptyList(),
        coverId = this.covers?.firstOrNull(),
        publishYear = null,
        description = this.description?.value,
        subjectPlaces = this.subjects,
        subjectPeople = null,
        subjectTimes = null,
        rating = 0,
        isRead = false,
        isFavorite = false
    )
}
fun BookDetailDto.toDboMerging(existingDbo: BookDbo?): BookDbo {
    return BookDbo(
        // ID y Título: probablemente siempre del BookDetailDto si está disponible
        id = this.key ?: existingDbo?.id ?: "",
        title = this.title ?: existingDbo?.title,

        // Autores: BookDetailDto es más específico aquí
        author = this.authors?.map { it.author.key } ?: existingDbo?.author ?: emptyList(),

        // CoverId: BookDetailDto puede tener una lista de portadas, toma la primera o la existente
        coverId = this.covers?.firstOrNull() ?: existingDbo?.coverId,

        // PublishYear: ¡CLAVE! Si BookDetailDto no lo da, usa el de existingDbo
        publishYear = existingDbo?.publishYear, // <--- MANTÉN EL VALOR DEL DBO EXISTENTE
        //      SI BookDetailDto NO PROVEE UNO.
        //      Si BookDetailDto SÍ lo proveyera, sería:
        //      this.publishYearFromDetailDto ?: existingDbo?.publishYear

        // Description: Prioriza la del BookDetailDto
        description = this.description?.value ?: existingDbo?.description,

        // Subjects/Places: Prioriza BookDetailDto
        subjectPlaces = this.subjects ?: existingDbo?.subjectPlaces,

        // SubjectPeople: Si BookDetailDto no lo da, usa el de existingDbo
        subjectPeople = existingDbo?.subjectPeople, // <--- MANTÉN

        // SubjectTimes: Si BookDetailDto no lo da, usa el de existingDbo
        subjectTimes = existingDbo?.subjectTimes,   // <--- MANTÉN

        // Rating, isRead, isFavorite: ESTOS SON ESTADOS DEL USUARIO,
        // NO DEBERÍAN SER SOBRESCRITOS POR LA API A MENOS QUE LA API LOS GESTIONE.
        // Aquí asumimos que la API NO gestiona estos estados.
        rating = existingDbo?.rating ?: 0,
        isRead = existingDbo?.isRead ?: false,
        isFavorite = existingDbo?.isFavorite ?: false
    )
}
