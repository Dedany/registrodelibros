package com.dedany.registrodelibros.di

import com.dperez.data.datasource.local.dao.AuthorDao
import com.dperez.data.datasource.local.dao.BookAuthorDao
import com.dperez.data.datasource.local.dao.BookDao
import com.dperez.data.datasource.local.localdatasource.AuthorLocalDataSource
import com.dperez.data.datasource.local.localdatasource.BookAuthorLocalDataSource
import com.dperez.data.datasource.local.localdatasource.BookLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataSourceModule {

    @Provides
    @Singleton
    fun provideBookLocalDataSource(bookDao: BookDao): BookLocalDataSource {
        return BookLocalDataSource(bookDao)
    }

    @Provides
    @Singleton
    fun provideAuthorLocalDataSource(authorDao: AuthorDao): AuthorLocalDataSource {
        return AuthorLocalDataSource(authorDao)
    }

    @Provides
    @Singleton
    fun provideBookAuthorLocalDataSource(bookAuthorDao: BookAuthorDao): BookAuthorLocalDataSource {
        return BookAuthorLocalDataSource(bookAuthorDao)
    }

}