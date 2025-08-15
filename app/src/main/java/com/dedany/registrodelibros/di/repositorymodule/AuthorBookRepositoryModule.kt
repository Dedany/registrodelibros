package com.dedany.registrodelibros.di.repositorymodule

import com.dedany.domain.repository.BookAuthorRepository
import com.dperez.data.datasource.local.localdatasource.AuthorLocalDataSource
import com.dperez.data.repository.BookAuthorRepositoryImpl
import com.dperez.data.datasource.local.localdatasource.BookAuthorLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookAuthorRepositoryModule {

    @Provides
    @Singleton
    fun provideBookAuthorRepository(
        localDataSource: BookAuthorLocalDataSource
    ): BookAuthorRepository {
        return BookAuthorRepositoryImpl(localDataSource)
    }
}