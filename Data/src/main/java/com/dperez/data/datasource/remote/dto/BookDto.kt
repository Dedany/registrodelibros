package com.dperez.data.datasource.remote.dto


data class BookDto (
    @SerializedName("title") val title: String?,
    @SerializedName("author_name") val authorName: List<String>?,
    @SerializedName("cover_i") val coverId: Int?,
    @SerializedName("first_publish_year") val publishYear: Int?,
    @SerializedName("key") val key: String?
)

