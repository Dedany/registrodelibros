package com.dperez.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dperez.data.datasource.local.dbo.BookDbo

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: BookDbo)

    @Delete
    suspend fun deleteBook(book: BookDbo)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookDbo?

    @Query("SELECT * FROM books WHERE author LIKE '%' || :author || '%'")
    suspend fun getBooksByAuthor(author: String): List<BookDbo>

    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%'")
    suspend fun getBooksByTitle(title: String): List<BookDbo>
}