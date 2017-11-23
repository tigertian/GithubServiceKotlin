package com.example.kotlin.livedataroomretrofitkotlindemo

/**
 * Created by tianlu on 2017/11/17.
 */

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.example.kotlin.livedataroomretrofitkotlindemo.database.Database
import android.arch.persistence.room.Room as RoomDB

class MainApplication : Application() {

    companion object {
        lateinit var database: Database
    }

    override fun onCreate() {
        super.onCreate()

        database = RoomDB.databaseBuilder(this, classOf<Database>(), "room_sample.db")
                //.allowMainThreadQueries() //Allowed to run on the main thread
                //.fallbackToDestructiveMigration() //Allowed to fallback on migration failed
                .build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}