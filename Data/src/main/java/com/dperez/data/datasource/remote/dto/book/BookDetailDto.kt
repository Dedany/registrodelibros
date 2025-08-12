package com.dperez.data.datasource.remote.dto.book

import com.google.gson.annotations.SerializedName

data class BookDetailDto(
    @SerializedName("title") val title: String?,
    @SerializedName("key") val key: String?,
    @SerializedName("description") val description: DescriptionField?,
    @SerializedName("covers") val covers: List<Int>?,
    @SerializedName("subjects") val subjects: List<String>?,
    @SerializedName("authors") val authors: List<AuthorWrapper>?,

)

data class DescriptionField(
    @SerializedName("value") val value: String?
)

data class AuthorWrapper(
    @SerializedName("author") val author: AuthorKey
)

data class AuthorKey(
    @SerializedName("key") val key: String
)
