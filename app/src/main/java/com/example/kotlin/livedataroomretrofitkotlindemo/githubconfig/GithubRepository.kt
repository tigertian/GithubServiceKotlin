package com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.PerActivity
import com.example.kotlin.livedataroomretrofitkotlindemo.database.UserDao
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Resource
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by tianlu on 2017/11/23.
 */
@PerActivity
class GithubRepository  {

    var token : String? = null
    var dao : UserDao? = null
    var executor : Executor? = null
    var data = MediatorLiveData<Resource<User>>()

    @Inject
    constructor(
                //@GithubTokenQualifier
                githubToken : String,
                userDao : UserDao,
                exec : Executor){
        token = githubToken
        dao = userDao
        executor = exec
    }

    fun getUser(name : String) : LiveData<Resource<User>>? {
        var user = dao?.findByName(name)
        if(user != null)
            data.value = Resource.success(user)
        else
            data.value = Resource.loading(null)
        refreshUser(name)

        return data
    }

    private fun refreshUser(name : String){

        GithubService.createGithubServiceRepo(token!!)!!.getUser(name).enqueue(object : Callback<User>{
            override fun onResponse(call : Call<User>, response : Response<User>){
                executor?.execute({->
                    val user = response.body()!!
                    user.name = name

                    val count = dao?.countByName(name)
                    if(count == 0) {
                        dao?.createUser(user)
                    }else{
                        dao?.updateUser(user)
                    }

                    data.postValue(Resource.success(user))
                })
            }

            override fun onFailure(call : Call<User>, throwable : Throwable){
                data.postValue(Resource.error(throwable.message + "", null))
            }
        })

    }

}