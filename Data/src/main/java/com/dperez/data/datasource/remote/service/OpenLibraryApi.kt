package com.dperez.data.datasource.remote.service


import com.dperez.data.datasource.remote.dto.author.AuthorDto
import com.dperez.data.datasource.remote.dto.author.AuthorSearchResponseDto
import com.dperez.data.datasource.remote.dto.author.WorksByAuthorDto
import com.dperez.data.datasource.remote.dto.book.BookDetailDto
import com.dperez.data.datasource.remote.dto.book.SearchResponseDto
import com.dperez.data.datasource.remote.dto.book.SubjectDto
import retrofit2.Response
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
        @Query("sort") sort: String? = null
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

    @GET("authors/{authorId}/works.json")
    suspend fun getWorksByAuthor(
        @Path("authorId") authorId: String,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): WorksByAuthorDto?


    @GET("works/{workId}.json")
    suspend fun getBookById(
        @Path("workId") bookId: String
    ): BookDetailDto

    @GET("subjects/{subject}.json")
    suspend fun searchBooksBySubject(
        @Path("subject" ,encoded=true) subject: String,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<SubjectDto>


}