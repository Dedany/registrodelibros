package com.dperez.data.datasource.remote.dto.author

import com.google.gson.annotations.SerializedName

data class WorksByAuthorDto(
    @SerializedName("entries") val entries: List<WorkEntryDto>?,
    @SerializedName("size") val size: Int?,
)
data class WorkEntryDto(
    @SerializedName("title") val title: String?,
    @SerializedName("key") val key: String?,


    @SerializedName("authors") val authors: List<WorkAuthorStubDto>?,

    @SerializedName("type") val type: TypeKeyDto?,
    @SerializedName("covers") val covers: List<Int>?,


    @SerializedName("description") val description: WorkDescriptionField?,


    @SerializedName("first_publish_date") val firstPublishDate: String?,
    @SerializedName("subjects") val subjects: List<String>?,

    @SerializedName("latest_revision") val latestRevision: Int?,
    @SerializedName("revision") val revision: Int?,
    @SerializedName("created") val created: CreatedLastModifiedDto?,
    @SerializedName("last_modified") val lastModified: CreatedLastModifiedDto?

)


data class WorkAuthorStubDto(

    @SerializedName("type") val type: TypeKeyDto?,
    @SerializedName("author") val author: AuthorKeyDto?
)
data class AuthorKeyDto(
    @SerializedName("key") val key: String?
)

data class TypeKeyDto(
    @SerializedName("key") val key: String?
)


data class WorkDescriptionDto(

    @SerializedName("type") val type: String?,
    @SerializedName("value") val value: String?
)

data class CreatedLastModifiedDto(
    @SerializedName("type") val type: String?,
    @SerializedName("value") val value: String?
)

sealed class WorkDescriptionField {
    data class DescriptionValue(val value: String) :
        WorkDescriptionField()

    data class DescriptionString(val description: String) : WorkDescriptionField()
}