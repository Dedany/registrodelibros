package com.dperez.data.datasource.local.converters

import androidx.room.TypeConverter
import com.dperez.data.datasource.remote.dto.author.BioField
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromList(value: List<String>?): String? {
        return value?.joinToString(separator = "|")
    }

    @TypeConverter
    fun toList(value: String?): List<String>? {
        return value?.split("|")
    }

    @TypeConverter
    fun fromBioField(bioField: BioField?): String? {
        return gson.toJson(bioField)
    }

    @TypeConverter
    fun toBioField(data: String?): BioField? {
        if (data == null) return null
        val type = object : TypeToken<BioField>() {}.type
        return gson.fromJson(data, type)
    }
}