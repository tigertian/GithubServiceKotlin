package com.example.kotlin.livedataroomretrofitkotlindemo

/**
 * Created by tianlu on 2017/11/17.
 */

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.ApplicationComponent
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.ApplicationModule
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.DaggerApplicationComponent
import com.example.kotlin.livedataroomretrofitkotlindemo.database.Database
import android.arch.persistence.room.Room as RoomDB


class MainApplication : Application() {
    protected var applicationComponent: ApplicationComponent? = null

    companion object {
        lateinit var database: Database

        fun get(context: Context): MainApplication {
            return context.applicationContext as MainApplication
        }
    }


    override fun onCreate() {
        super.onCreate()

        database = RoomDB.databaseBuilder(this, classOf<Database>(), "room_sample.db")
                //.allowMainThreadQueries() //Allowed to run on the main thread
                //.fallbackToDestructiveMigration() //Allowed to fallback on migration failed
                .build()

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build();
        applicationComponent!!.inject(this);
    }

    fun getComponent(): ApplicationComponent {
        return applicationComponent!!
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}