package com.dedany.registrodelibros.di.repositorymodule

import com.dedany.domain.repository.AuthorRepository
import com.dperez.data.repository.AuthorRepositoryImpl
import com.dperez.data.datasource.local.localdatasource.AuthorLocalDataSource
import com.dperez.data.datasource.remote.remotedatasource.AuthorRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthorRepositoryModule {

    @Provides
    @Singleton
    fun provideAuthorRepository(
        localDataSource: AuthorLocalDataSource,
        remoteDataSource: AuthorRemoteDataSource
    ): AuthorRepository {
        return AuthorRepositoryImpl(localDataSource, remoteDataSource)
    }
}