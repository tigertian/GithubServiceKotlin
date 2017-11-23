package com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig

import android.arch.lifecycle.LiveData
import com.example.kotlin.livedataroomretrofitkotlindemo.database.UserDao
import com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig.GithubService
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * Created by tianlu on 2017/11/23.
 */
class GithubRepository constructor(githubToken : String, userDao : UserDao, exec : Executor)  {

    val token = githubToken
    val executor = exec
    val dao = userDao

    fun getUser(name : String) : LiveData<User>? {
        refreshUser(name)

        return dao.findByName(name)
    }

    private fun refreshUser(name : String){

        GithubService.createGithubServiceRepo(token)!!.getUser(name)!!.enqueue(object : Callback<User>{
            override fun onResponse(call : Call<User>, response : Response<User>){
                executor.execute({->
                    val count = dao.countByName(response.body()!!.name)
                    if(count == 0)
                        dao.createUser(response.body()!!)
                    else{
                        dao.updateUser(response.body()!!)
                    }
                })
            }

            override fun onFailure(call : Call<User>, throwable : Throwable){
                println(throwable)
            }
        })

    }

}