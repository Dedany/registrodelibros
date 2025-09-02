package com.dperez.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.dperez.data.datasource.local.dbo.AuthorWithBooks
import com.dperez.data.datasource.local.dbo.BookAuthorCrossRef
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.local.dbo.BookWithAuthors

@Dao
interface BookAuthorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRef(crossRef: BookAuthorCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrossRefs(crossRefs: List<BookAuthorCrossRef>)

    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookWithAuthors(bookId: String): BookWithAuthors?

    @Transaction
    @Query("SELECT * FROM authors WHERE id = :authorId")
    suspend fun getAuthorWithBooks(authorId: String): AuthorWithBooks?


    @Query(
        """
    SELECT DISTINCT b.*
    FROM books b
    INNER JOIN bookauthorcrossref bac ON b.id = bac.bookId
    INNER JOIN authors a ON bac.authorId = a.id
    WHERE a.name LIKE '%' || :authorName || '%'
    """
    )
    suspend fun getBooksByAuthorName(authorName: String): List<BookDbo>

    @Query("SELECT * FROM bookauthorcrossref WHERE bookId = :bookId")
    suspend fun getCrossRefsForBook(bookId: String): List<BookAuthorCrossRef>
}