package com.dperez.data.datasource.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dperez.data.datasource.local.dbo.AuthorDbo
import com.dperez.data.datasource.local.dbo.BookDbo


interface AuthorDao {
    @Query("SELECT * FROM authors")
    suspend fun getAllAuthors(): List<AuthorDbo>

    @Query("SELECT * FROM authors WHERE id = :authorId")
    suspend fun getAuthorById(authorId: String): AuthorDbo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthor(author: AuthorDbo)

    @Query("SELECT * FROM authors WHERE name LIKE '%' || :name || '%'")
    suspend fun getAuthorsByName(name: String): List<AuthorDbo>

    @Query("UPDATE authors SET isFavorite = :isFavorite WHERE id = :authorId")
    suspend fun setAuthorFavorite(authorId: String, isFavorite: Boolean)

    @Query("SELECT * FROM authors WHERE isFavorite = 1")
    suspend fun getFavoriteAuthors(): List<AuthorDbo>




}