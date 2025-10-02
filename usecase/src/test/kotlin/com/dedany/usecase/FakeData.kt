package com.dedany.usecase

import com.dedany.domain.entities.Author
import com.dedany.domain.entities.Book

object FakeData {
    fun fakeBook(
        id: String = "123",
        title: String? = "Libro Test",
        coverId: Int? = 1,
        publishYear: Int? = 2025,
        description: String? = "Descripción",
        subjects: List<String>? = listOf("Ficción"),
        rating: Int = 0,
        isRead: Boolean = false,
        isFavorite: Boolean = false
    ) : Book {
        return Book(id, title, coverId, publishYear, description, subjects, rating, isRead, isFavorite)

    }

    fun fakeAuthor(
        id: String = "1",
        name: String = "Daniel Pérez",
        birthDate: String? = null,
        topWork: String? = null,
        workCount: Int? = null,
        alternateNames: List<String>? = emptyList(),
        topSubjects: List<String>? = emptyList(),
        bio: String? = null,
        isFavorite: Boolean = false
    ): Author {
        return Author(id = id, name = name, birthDate = birthDate, topWork = topWork, workCount = workCount, alternateNames = alternateNames, topSubjects = topSubjects, bio = bio, isFavorite = isFavorite
        )
    }
}