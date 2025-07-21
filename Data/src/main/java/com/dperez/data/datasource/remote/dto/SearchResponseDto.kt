package com.dperez.data.datasource.remote.dto

data class SearchResponseDto(
@SerializedName("start") val start: Int,
@SerializedName("num_found") val numFound: Int,
@SerializedName("docs") val books: List<BookDto>
)
