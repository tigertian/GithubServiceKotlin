package com.example.kotlin.livedataroomretrofitkotlindemo

/**
 * Created by tianlu on 2017/11/17.
 */
internal inline fun <reified T : Any> classOf() = T::class.java

internal fun getId(): Long = Math.random().toLong()