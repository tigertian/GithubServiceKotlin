package com.example.kotlin.livedataroomretrofitkotlindemo.dagger2

import android.app.Application
import android.content.Context

import dagger.Module
import dagger.Provides


/**
 * Created by tianlu on 2017/11/24.
 */
@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return mApplication
    }

    @Provides
    internal fun provideApplication(): Application {
        return mApplication
    }

}