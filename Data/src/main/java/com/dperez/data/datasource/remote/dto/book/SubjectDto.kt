package com.dperez.data.datasource.remote.dto.book

import com.google.gson.annotations.SerializedName


data class SubjectDto(
    @SerializedName("name") val name: String?,
    @SerializedName("subject_type") val subjectType: String?,
    @SerializedName("work_count") val workCount: Int?,
    @SerializedName("works") val works: List<WorkDto>
)

data class WorkDto(
    @SerializedName("key") val key: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("authors") val authors: List<WorkAuthorDto>?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?,
    @SerializedName("edition_count") val editionCount: Int?,
    @SerializedName("cover_id") val coverId: Int?,
    @SerializedName("has_fulltext") val hasFulltext: Boolean?
)

data class WorkAuthorDto(
    @SerializedName("key") val key: String?,
    @SerializedName("name") val name: String?
)
