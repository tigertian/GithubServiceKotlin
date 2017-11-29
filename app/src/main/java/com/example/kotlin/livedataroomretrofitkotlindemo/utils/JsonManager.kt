package com.example.kotlin.livedataroomretrofitkotlindemo.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

/**
 * For Gson parsing objects to json string and parsing the json string back to objects
 * @author tianlu
 */
object JsonManager {


    /**
     * Build the GSON instance
     * @return GSON instance
     */
    val gson: Gson
        get() {
            val builder = GsonBuilder()
            builder.registerTypeAdapter(classOf<Date>(), DateTypeAdapter())
            builder.registerTypeAdapter(classOf<HashMap<String, Any>>(), MapTypeAdapter())
            builder.setPrettyPrinting()
            return builder.create()
        }

}