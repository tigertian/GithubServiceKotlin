package com.example.kotlin.livedataroomretrofitkotlindemo.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.kotlin.livedataroomretrofitkotlindemo.utils.classOf
import javax.inject.Inject


/**
 * Created by tianlu on 2017/11/24.
 */
class GithubViewModelFactory @Inject
constructor(private val mViewModel: UserViewModel) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(classOf<UserViewModel>())) {
            return mViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}