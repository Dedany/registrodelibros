package com.dperez.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dperez.data.datasource.local.converters.Converters
import com.dperez.data.datasource.local.dbo.BookDbo

@Database(
    entities = [BookDbo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}