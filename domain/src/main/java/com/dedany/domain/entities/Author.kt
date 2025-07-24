package com.dedany.domain.entities

data class Author(
    val id: String,
    val name: String,
    val birthDate: String?,
    val topWork: String?,
    val workCount: Int?,
    val alternateNames: List<String>?,
    val topSubjects: List<String>?,
    val isFavorite: Boolean = false

)
