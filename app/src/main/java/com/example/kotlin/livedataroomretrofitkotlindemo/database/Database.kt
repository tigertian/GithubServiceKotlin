package com.example.kotlin.livedataroomretrofitkotlindemo.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User

/**
 * Created by tianlu on 2017/11/17.
 */
@Database(entities = arrayOf(User::class), version = 2)
abstract class Database : RoomDatabase(){
    abstract fun userDao() : UserDao
}