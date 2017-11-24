package com.example.kotlin.livedataroomretrofitkotlindemo.dagger2

import android.app.Application
import android.content.Context
import com.example.kotlin.livedataroomretrofitkotlindemo.MainApplication
import dagger.Component
import javax.inject.Singleton



/**
 * Created by tianlu on 2017/11/24.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    @get:ApplicationContext
    val context: Context

    val application: Application

    fun inject(mainApplication: MainApplication)

}