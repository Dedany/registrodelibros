package com.dedany.domain.entities

data class Book(
    val id: String,
    val title: String?,
    val coverId: Int?,
    val publishYear: Int?,
    val description: String?,
    val subjects: List<String>?,
    val rating: Int = 0,
    val isRead: Boolean = false,
    val isFavorite: Boolean = false,
)
