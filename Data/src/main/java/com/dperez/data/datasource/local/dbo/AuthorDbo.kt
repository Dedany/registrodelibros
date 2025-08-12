package com.dperez.data.datasource.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorDbo (
    @PrimaryKey val id: String,
    val name: String,
    val birthDate: String?,
    val topWork: String?,
    val workCount: Int?,
    val alternateNames: List<String>?,
    val topSubjects: List<String>?,
    val bio: String?,
    val isFavorite: Boolean = false

)