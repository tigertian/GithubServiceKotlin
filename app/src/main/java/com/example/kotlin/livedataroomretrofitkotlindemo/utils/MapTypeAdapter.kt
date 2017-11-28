package com.example.kotlin.livedataroomretrofitkotlindemo.utils

import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

/**
 * Process key-value pair
 * @author tianlu
 */
class MapTypeAdapter : JsonDeserializer<Any> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any? {
        return if (json.isJsonNull)
            null
        else if (json.isJsonPrimitive)
            handlePrimitive(json.asJsonPrimitive)
        else if (json.isJsonArray)
            handleArray(json.asJsonArray, context)
        else
            handleObject(json.asJsonObject, context)
    }

    private fun handlePrimitive(json: JsonPrimitive): Any {
        if (json.isBoolean)
            return json.asBoolean
        else if (json.isString)
            return json.asString
        else {
            val bigDec = json.asBigDecimal
            // Find out if it is an int type
            try {
                bigDec.toBigIntegerExact()
                try {
                    return bigDec.intValueExact()
                } catch (e: ArithmeticException) {
                }

                return bigDec.toLong()
            } catch (e: ArithmeticException) {
            }

            // Just return it as a double
            return bigDec.toDouble()
        }
    }

    private fun handleArray(json: JsonArray, context: JsonDeserializationContext): Any {
        val array = arrayOfNulls<Any>(json.size())
        for (i in array.indices)
            array[i] = context.deserialize<Any>(json.get(i), Any::class.java)
        return array
    }

    private fun handleObject(json: JsonObject, context: JsonDeserializationContext): Any {
        val map = HashMap<String, Any>()
        for ((key, value) in json.entrySet())
            map.put(key, context.deserialize(value, Any::class.java))
        return map
    }
}