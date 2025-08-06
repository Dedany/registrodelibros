package com.dedany.registrodelibros.di.usecasemodule

import com.dedany.domain.repository.BookAuthorRepository
import com.dedany.usecase.bookAuthor.GetAuthorWithBooksUseCase
import com.dedany.usecase.bookAuthor.GetBookWithAuthorsUseCase
import com.dedany.usecase.bookAuthor.InsertCrossRefUseCase
import com.dedany.usecase.bookAuthor.InsertCrossRefsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookAuthorUseCaseModule {

    @Provides
    @Singleton
    fun provideInsertCrossRefUseCase(repository: BookAuthorRepository): InsertCrossRefUseCase {
        return InsertCrossRefUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInsertCrossRefsUseCase(repository: BookAuthorRepository): InsertCrossRefsUseCase {
        return InsertCrossRefsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBookWithAuthorsUseCase(repository: BookAuthorRepository): GetBookWithAuthorsUseCase {
        return GetBookWithAuthorsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAuthorWithBooksUseCase(repository: BookAuthorRepository): GetAuthorWithBooksUseCase {
        return GetAuthorWithBooksUseCase(repository)
    }
}