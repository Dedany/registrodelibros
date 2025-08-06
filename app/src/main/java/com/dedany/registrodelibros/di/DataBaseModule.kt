package com.dedany.registrodelibros.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dperez.data.datasource.local.BookDatabase
import com.dperez.data.datasource.local.dao.AuthorDao
import com.dperez.data.datasource.local.dao.BookAuthorDao
import com.dperez.data.datasource.local.dao.BookDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "registro_libros_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookDao(db: BookDatabase): BookDao = db.bookDao()

    @Provides
    @Singleton
    fun provideAuthorDao(db: BookDatabase): AuthorDao = db.authorDao()

    @Provides
    @Singleton
    fun provideBookAuthorDao(db: BookDatabase): BookAuthorDao = db.bookAuthorDao()
}