package com.dedany.domain.entities

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val coverUrl: String? = null,
    val publishYear: Int? = null,
    val rating: Int = 0,
    val isRead: Boolean = false
)
