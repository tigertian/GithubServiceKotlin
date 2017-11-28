package com.example.kotlin.livedataroomretrofitkotlindemo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.PerActivity
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Resource
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Subscription
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import com.example.kotlin.livedataroomretrofitkotlindemo.network.githubconfig.GithubRepository
import javax.inject.Inject

/**
 * Created by tianlu on 2017/11/23.
 */
@PerActivity
class UserViewModel : ViewModel {

    var userRepo : GithubRepository
    private var user : LiveData<Resource<User>>? = null
    private var subscriptions : LiveData<Resource<List<Subscription>>>? = null

    @Inject
    constructor(repo : GithubRepository){
        userRepo = repo
    }

    fun initUser(name : String){
        user = userRepo.getUser(name)
    }

    fun getUser() : LiveData<Resource<User>>? {
        return user!!
    }

    fun initSubscriptions(username : String){
        subscriptions = userRepo.getSubscriptions(username)
    }

    fun getSubscriptions() : LiveData<Resource<List<Subscription>>>? {
        return subscriptions!!
    }

}