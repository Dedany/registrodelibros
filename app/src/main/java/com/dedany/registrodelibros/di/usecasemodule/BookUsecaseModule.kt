package com.dedany.registrodelibros.di.usecasemodule

import com.dedany.domain.repository.BookRepository
import com.dedany.usecase.book.DeleteBookUseCase
import com.dedany.usecase.book.GetAllBooksUseCase
import com.dedany.usecase.book.GetBookByIdUseCase
import com.dedany.usecase.book.GetBooksByAuthorUseCase
import com.dedany.usecase.book.GetBooksByTitleUseCase
import com.dedany.usecase.book.GetFavoriteBooksUseCase
import com.dedany.usecase.book.SaveBookUseCase
import com.dedany.usecase.book.SetBookFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookUseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllBooksUseCase(repository: BookRepository): GetAllBooksUseCase {
        return GetAllBooksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveBookUseCase(repository: BookRepository): SaveBookUseCase {
        return SaveBookUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteBookUseCase(repository: BookRepository): DeleteBookUseCase {
        return DeleteBookUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetBookFavoriteUseCase(repository: BookRepository): SetBookFavoriteUseCase {
        return SetBookFavoriteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteBooksUseCase(repository: BookRepository): GetFavoriteBooksUseCase {
        return GetFavoriteBooksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBookByIdUseCase(repository: BookRepository): GetBookByIdUseCase {
        return GetBookByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBooksByAuthorUseCase(repository: BookRepository): GetBooksByAuthorUseCase {
        return GetBooksByAuthorUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBooksByTitleUseCase(repository: BookRepository): GetBooksByTitleUseCase {
        return GetBooksByTitleUseCase(repository)
    }
}
