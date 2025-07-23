package com.dperez.data.datasource.remote.dto.author

import com.google.gson.annotations.SerializedName

data class AuthorDto(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("birth_date") val birthDate: String?,
    @SerializedName("top_work") val topWork: String?,
    @SerializedName("work_count") val workCount: Int?,
    @SerializedName("alternate_names") val alternateNames: List<String>?,
    @SerializedName("top_subjects") val topSubjects: List<String>?
)
