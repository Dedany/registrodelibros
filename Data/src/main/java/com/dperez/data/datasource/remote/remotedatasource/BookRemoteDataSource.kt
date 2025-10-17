package com.dperez.data.datasource.remote.remotedatasource

import com.dperez.data.datasource.remote.dto.book.BookDetailDto
import com.dperez.data.datasource.remote.dto.book.BookDto
import com.dperez.data.datasource.remote.dto.book.SearchResponseDto
import com.dperez.data.datasource.remote.dto.book.SubjectDto
import com.dperez.data.datasource.remote.service.OpenLibraryApi
import retrofit2.Response
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val api: OpenLibraryApi
) {
    suspend fun searchBooksByTitle(title: String, page: Int? = null): SearchResponseDto {
        return api.searchBooksByTitle(title, page)
    }

    suspend fun searchBooksByAuthor(
        author: String,
        page: Int? = null,
        sort: String? = null
    ): SearchResponseDto {
        return api.searchBooksByAuthor(author, page, sort)
    }

    suspend fun searchBooksByQuery(query: String, page: Int? = null): SearchResponseDto {
        return api.searchBooksByQuery(query, page)
    }
    suspend fun getBookById(bookId: String): BookDetailDto {
        return api.getBookById(bookId)
    }
    suspend fun searchBooksBySubject(
        subject: String,
        limit: Int = 50,
        offset: Int = 0
    ): Response<SubjectDto> {
        return api.searchBooksBySubject(subject, limit, offset)
    }

}