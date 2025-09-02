package com.dedany.registrodelibros.di.repositorymodule

import com.dedany.domain.repository.AuthorRepository
import com.dedany.domain.repository.BookAuthorRepository
import com.dedany.domain.repository.BookRepository
import com.dperez.data.repository.BookRepositoryImpl
import com.dperez.data.datasource.local.localdatasource.BookLocalDataSource
import com.dperez.data.datasource.remote.remotedatasource.BookRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookRepositoryModule {

    @Provides
    @Singleton
    fun provideBookRepository(
        localDataSource: BookLocalDataSource,
        remoteDataSource: BookRemoteDataSource,
        authorRepository: AuthorRepository,
        bookAuthorRepository: BookAuthorRepository
    ): BookRepository {
        return BookRepositoryImpl(localDataSource, remoteDataSource, authorRepository, bookAuthorRepository)
    }
}