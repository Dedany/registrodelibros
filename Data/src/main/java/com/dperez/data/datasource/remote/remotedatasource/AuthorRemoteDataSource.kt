package com.dperez.data.datasource.remote.remotedatasource

import android.content.ContentValues.TAG
import android.util.Log
import com.dperez.data.datasource.remote.dto.author.AuthorDto
import com.dperez.data.datasource.remote.dto.author.AuthorSearchResponseDto
import com.dperez.data.datasource.remote.dto.author.WorksByAuthorDto
import com.dperez.data.datasource.remote.service.OpenLibraryApi
import javax.inject.Inject

class AuthorRemoteDataSource @Inject constructor(
    private val api: OpenLibraryApi
) {
    suspend fun searchAuthors(query: String): AuthorSearchResponseDto? {
        val cleanQuery = query.trim()
        return try {
            Log.d(TAG, "Searching authors from remote with query: \"$query\"")
            val response = api.searchAuthors(cleanQuery)
            Log.i(TAG, "Successfully searched authors. Found: ${response.numFound}")
            response
        } catch (e: Exception) {
            Log.e(TAG, "Error searching authors with query: \"$query\". Exception: ${e.message}", e)
            null
        }
    }

    suspend fun getAuthorById(authorId: String): AuthorDto? {
        return try {
            Log.d(TAG, "Fetching author by ID from remote: $authorId")
            val author = api.getAuthorById(authorId)
            Log.i(TAG, "Successfully fetched author: ${author?.name ?: authorId}")
            author
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching author by ID: $authorId. Exception: ${e.message}", e)
            null
        }
    }

    suspend fun getWorksByAuthorId(authorId: String): WorksByAuthorDto? {
        return try {

            api.getWorksByAuthor(authorId)
        } catch (e: Exception) {
            Log.e("AuthorRemoteDS", "Error fetching works for author $authorId", e)
            null
        }
    }

}