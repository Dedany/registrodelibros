package com.dperez.data.datasource.remote.service


import com.dperez.data.datasource.remote.dto.author.AuthorDto
import com.dperez.data.datasource.remote.dto.author.AuthorSearchResponseDto
import com.dperez.data.datasource.remote.dto.book.BookDetailDto
import com.dperez.data.datasource.remote.dto.book.BookDto
import com.dperez.data.datasource.remote.dto.book.SearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface OpenLibraryApi {

    @GET("search.json")
    suspend fun searchBooksByTitle(
        @Query("title") title: String,
        @Query("page") page: Int? = null
    ): SearchResponseDto

    @GET("search.json")
    suspend fun searchBooksByAuthor(
        @Query("author") author: String,
        @Query("page") page: Int? = null,
        @Query("sort") sort: String? = null // ej. "new"
    ): SearchResponseDto

    @GET("search.json")
    suspend fun searchBooksByQuery(
        @Query("q") query: String,
        @Query("page") page: Int? = null
    ): SearchResponseDto

    @GET("search/authors.json")
    suspend fun searchAuthors(
        @Query("q") query: String
    ): AuthorSearchResponseDto

    @GET("authors/{authorId}.json")
    suspend fun getAuthorById(
        @retrofit2.http.Path("authorId") authorId: String
    ): AuthorDto

    @GET("works/{workId}.json")
    suspend fun getBookById(
        @Path("workId") bookId: String
    ): BookDetailDto
}