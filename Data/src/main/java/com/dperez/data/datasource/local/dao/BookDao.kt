package com.dperez.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.local.dbo.BookDetailDbo

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<BookDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBooks(books: List<BookDbo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: BookDbo)

    @Query("UPDATE books SET isFavorite = :isFavorite WHERE id = :bookId")
    suspend fun setBookFavorite(bookId: String, isFavorite: Boolean)

    @Query("SELECT * FROM books WHERE isFavorite = 1")
    suspend fun getFavoriteBooks(): List<BookDbo>

    @Delete
    suspend fun deleteBook(book: BookDbo)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: String): BookDbo?

    @Query("SELECT * FROM books WHERE title LIKE '%' || :title || '%'")
    suspend fun getBooksByTitle(title: String): List<BookDbo>

    @Query("SELECT * FROM books WHERE subjects LIKE '%' || :genre || '%'")
    suspend fun getBooksByGenre(genre: String): List<BookDbo>


}
