package com.example.kotlin.livedataroomretrofitkotlindemo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig.GithubRepository
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User

/**
 * Created by tianlu on 2017/11/23.
 */
class UserViewModel() : ViewModel() {

    lateinit var userRepo : GithubRepository
    var user : LiveData<User>? = null

    constructor(repo : GithubRepository) : this() {
        userRepo = repo
    }

    fun initUser(name : String){
        if(user != null)
            return

        user = userRepo.getUser(name)
    }

}