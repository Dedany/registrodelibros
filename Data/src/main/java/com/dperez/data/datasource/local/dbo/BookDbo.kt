package com.dperez.data.datasource.local.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookDbo(
    @PrimaryKey val id: String,
    val title: String?,
    val author: List<String>?,
    val coverId: Int?,
    val publishYear: Int?,
    val description: String?,
    val subjectPlaces: List<String>?,
    val subjectPeople: List<String>?,
    val subjectTimes: List<String>?,
    val rating: Int = 0,
    val isRead: Boolean = false,
    val isFavorite: Boolean = false,
)
