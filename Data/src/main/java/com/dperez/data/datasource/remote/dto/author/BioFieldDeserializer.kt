package com.dperez.data.datasource.remote.dto.author

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class BioFieldDeserializer : JsonDeserializer<BioField> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BioField? {
        if (json == null || json.isJsonNull) return null

        return when {
            json.isJsonPrimitive -> {
                // Si es un string simple
                BioField.BioString(json.asString)
            }
            json.isJsonObject -> {
                // Si es un objeto con campo "value"
                val jsonObj = json.asJsonObject
                val value = jsonObj.get("value")?.asString
                if (value != null) {
                    BioField.BioValue(value)
                } else {
                    null
                }
            }
            else -> null
        }
    }
}
