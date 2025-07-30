package com.dedany.registrodelibros.di

import com.dperez.data.datasource.remote.remotedatasource.AuthorRemoteDataSource
import com.dperez.data.datasource.remote.remotedatasource.BookRemoteDataSource
import com.dperez.data.datasource.remote.service.OpenLibraryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideBookRemoteDataSource(api: OpenLibraryApi): BookRemoteDataSource {
        return BookRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideAuthorRemoteDataSource(api: OpenLibraryApi): AuthorRemoteDataSource {
        return AuthorRemoteDataSource(api)
    }

}