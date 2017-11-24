package com.example.kotlin.livedataroomretrofitkotlindemo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.PerActivity
import com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig.GithubRepository
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import javax.inject.Inject

/**
 * Created by tianlu on 2017/11/23.
 */
@PerActivity
class UserViewModel : ViewModel {

    var userRepo : GithubRepository
    private var user : LiveData<User>? = null

    @Inject
    constructor(repo : GithubRepository){
        userRepo = repo
    }

    fun initUser(name : String){
        user = userRepo.getUser(name)
    }

    fun getUser() : LiveData<User>? {
        return user
    }

}