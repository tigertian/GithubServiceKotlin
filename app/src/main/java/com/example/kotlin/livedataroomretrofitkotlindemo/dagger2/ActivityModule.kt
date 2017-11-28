package com.example.kotlin.livedataroomretrofitkotlindemo.dagger2

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.kotlin.livedataroomretrofitkotlindemo.MainApplication
import com.example.kotlin.livedataroomretrofitkotlindemo.R
import com.example.kotlin.livedataroomretrofitkotlindemo.database.Database
import com.example.kotlin.livedataroomretrofitkotlindemo.database.UserDao
import com.example.kotlin.livedataroomretrofitkotlindemo.viewmodel.GithubViewModelFactory
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by tianlu on 2017/11/24.
 */
@Module
class ActivityModule(private val mActivity: Activity) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context {
        return mActivity
    }

    @Provides
    internal fun provideActivity(): Activity {
        return mActivity
    }

    @Provides
    fun provideGithubViewModelFactory(
            factory: GithubViewModelFactory
    ): ViewModelProvider.Factory {
        return factory
    }

    @Provides
    @GithubTokenQualifier
    fun provideGithubToken() : String {
        return mActivity.getString(R.string.github_access_token)
    }

    @Provides
    fun provideExecutors() : Executor {
        return Executors.newFixedThreadPool(5)
    }

    @Provides
    fun provideUserDao() : UserDao{
        return MainApplication.database.userDao()
    }

    @Provides
    fun provideDatabase() : Database{
        return MainApplication.database
    }
}