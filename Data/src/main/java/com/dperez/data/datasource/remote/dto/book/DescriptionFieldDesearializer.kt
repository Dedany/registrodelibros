package com.dperez.data.datasource.remote.dto.book // O donde tengas DescriptionField

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class DescriptionFieldDeserializer : JsonDeserializer<DescriptionField> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DescriptionField {
        json?.let {
            return when {
                it.isJsonObject -> {
                    // Es un objeto, como se espera normalmente
                    val value = it.asJsonObject?.get("value")?.asString
                    DescriptionField(value)
                }
                it.isJsonPrimitive && it.asJsonPrimitive.isString -> {
                    // Es un String directamente
                    DescriptionField(it.asString)
                }
                else -> {
                    // No es ni un objeto ni un String, o es nulo de una forma inesperada
                    // Puedes devolver un DescriptionField con valor nulo o lanzar una excepción
                    DescriptionField(null)
                    // throw JsonParseException("Tipo inesperado para DescriptionField: ${json.javaClass}")
                }
            }
        }
        // Si el json es nulo, devuelve un DescriptionField con valor nulo
        return DescriptionField(null)
    }
}
