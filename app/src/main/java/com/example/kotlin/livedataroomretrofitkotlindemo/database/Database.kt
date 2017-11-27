package com.example.kotlin.livedataroomretrofitkotlindemo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import com.example.kotlin.livedataroomretrofitkotlindemo.utils.classOf

/**
 * Created by tianlu on 2017/11/17.
 */
@Database(entities = arrayOf(User::class), version = 2, exportSchema = false)
@TypeConverters(DateTypeConverter::class)
abstract class Database : RoomDatabase(){
    abstract fun userDao(): UserDao
}