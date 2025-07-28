package com.dperez.data.datasource.remote.remotedatasource

import com.dperez.data.datasource.remote.dto.author.AuthorSearchResponseDto
import com.dperez.data.datasource.remote.service.OpenLibraryApi
import javax.inject.Inject

class AuthorRemoteDataSource @Inject constructor(
    private val api: OpenLibraryApi
){
    suspend fun searchAuthors(query: String) : AuthorSearchResponseDto{
        return api.searchAuthors(query)
    }
}

