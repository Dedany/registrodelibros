package com.dperez.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dperez.data.datasource.local.converters.Converters
import com.dperez.data.datasource.local.dbo.BookDbo
import com.dperez.data.datasource.local.dao.AuthorDao
import com.dperez.data.datasource.local.dbo.AuthorDbo
import com.dperez.data.datasource.local.dao.BookDao
import com.dperez.data.datasource.local.dbo.BookAuthorCrossRef
import com.dperez.data.datasource.local.dao.BookAuthorDao
import com.dperez.data.datasource.local.dbo.BookDetailDbo

@Database(
    entities = [
        BookDbo::class,
        AuthorDbo::class,
        BookAuthorCrossRef::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun authorDao(): AuthorDao
    abstract fun bookAuthorDao(): BookAuthorDao


}