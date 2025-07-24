package com.dperez.data.datasource.remote.dto.author

import com.google.gson.annotations.SerializedName

data class AuthorSearchResponseDto(
    @SerializedName("numFound") val numFound: Int,
    @SerializedName("start") val start: Int,
    @SerializedName("numFoundExact") val numFoundExact: Boolean,
    @SerializedName("docs") val authors: List<AuthorDto>
)
