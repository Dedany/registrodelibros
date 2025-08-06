package com.dedany.registrodelibros.di.usecasemodule

import com.dedany.domain.repository.AuthorRepository
import com.dedany.usecase.author.GetAllAuthorsUseCase
import com.dedany.usecase.author.GetAuthorByIdUseCase
import com.dedany.usecase.author.GetAuthorsByNameUseCase
import com.dedany.usecase.author.GetFavoriteAuthorsUseCase
import com.dedany.usecase.author.SaveAuthorUseCase
import com.dedany.usecase.author.SetAuthorFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthorUseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllAuthorsUseCase(repository: AuthorRepository): GetAllAuthorsUseCase {
        return GetAllAuthorsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAuthorByIdUseCase(repository: AuthorRepository): GetAuthorByIdUseCase {
        return GetAuthorByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveAuthorUseCase(repository: AuthorRepository): SaveAuthorUseCase {
        return SaveAuthorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAuthorsByNameUseCase(repository: AuthorRepository): GetAuthorsByNameUseCase {
        return GetAuthorsByNameUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetAuthorFavoriteUseCase(repository: AuthorRepository): SetAuthorFavoriteUseCase {
        return SetAuthorFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteAuthorsUseCase(repository: AuthorRepository): GetFavoriteAuthorsUseCase {
        return GetFavoriteAuthorsUseCase(repository)
    }
}
