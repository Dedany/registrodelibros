package com.dperez.data.datasource.remote.dto.author

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class WorkDescriptionFieldDeserializer : JsonDeserializer<WorkDescriptionField> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WorkDescriptionField? {
        if (json == null || json.isJsonNull) return null

        return when {
            json.isJsonPrimitive -> {
                // Si es un string simple
                WorkDescriptionField.DescriptionString(json.asString)
            }
            json.isJsonObject -> {
                // Si es un objeto, asumimos que tiene un campo "value"
                // La API a veces también tiene "type", pero nos centramos en "value" por simplicidad
                // como en tu BioField.
                val jsonObj = json.asJsonObject
                val value = jsonObj.get("value")?.asString
                // También podría tener un campo "type", ej: jsonObj.get("type")?.asString

                if (value != null) {
                    WorkDescriptionField.DescriptionValue(value)
                } else {
                    // Si es un objeto pero no tiene "value", podrías decidir qué hacer.
                    // ¿Quizás es un string vacío si el objeto está vacío? O null.
                    // Si el objeto pudiera tener otros campos y no "value", esta lógica cambiaría.
                    // Por ahora, si no hay "value", lo tratamos como nulo o no parseable.
                    Log.w("WorkDescDeserializer", "Objeto JSON para descripción no tiene campo 'value': $jsonObj")
                    null // O podrías retornar un DescriptionString("") si quieres un valor por defecto
                }
            }
            else -> {
                Log.w("WorkDescDeserializer", "Tipo de JSON inesperado para descripción: $json")
                null
            }
        }
    }
}