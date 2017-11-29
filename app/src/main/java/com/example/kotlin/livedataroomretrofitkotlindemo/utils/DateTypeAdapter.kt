package com.example.kotlin.livedataroomretrofitkotlindemo.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.util.*

/**
 * For Gson parsing the Date object in (de)serialization process
 *
 * @author tianlu
 */
class DateTypeAdapter : JsonSerializer<Date>, JsonDeserializer<Date> {

    override fun serialize(src: Date, type: Type, context: JsonSerializationContext): JsonElement {
        val dateString = DateUtils.format(src, DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_Z)

        return JsonPrimitive(dateString)
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date? {
        if (json !is JsonPrimitive) {
            throw JsonParseException("The date should be a string value")
        }
        try {
            return DateUtils.parse(json.getAsString(), DateUtils.DateFormatType.YYYY_MM_DD_T_HH_MM_SS_Z)
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }

    }

}