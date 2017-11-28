package com.example.kotlin.livedataroomretrofitkotlindemo.network.githubconfig

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.GithubTokenQualifier
import com.example.kotlin.livedataroomretrofitkotlindemo.dagger2.PerActivity
import com.example.kotlin.livedataroomretrofitkotlindemo.database.Database
import com.example.kotlin.livedataroomretrofitkotlindemo.database.UserDao
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Resource
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Subscription
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

    var token : String
    var dao : UserDao
    var executor : Executor
    var database : Database
    var dataUser = MediatorLiveData<Resource<User>>()
    var dataSubs = MediatorLiveData<Resource<List<Subscription>>>()

    @Inject
    constructor(@GithubTokenQualifier githubToken : String,
                userDao : UserDao,
                exec : Executor,
                db : Database){
        token = githubToken
        dao = userDao
        executor = exec
        database = db
    }

    //Get the users' observer
    fun getUser(name : String) : LiveData<Resource<User>>? {
        val user = dao.findUserByName(name)
        if(user != null)
            dataUser.value = Resource.success(user)
        else
            dataUser.value = Resource.loading(null)
        refreshUser(name)

        return dataUser
    }

    //Asynchronously refresh the user information
    private fun refreshUser(name : String){

        GithubService.createGithubServiceRepo(token)!!.getUser(name).enqueue(object : Callback<User>{
            override fun onResponse(call : Call<User>, response : Response<User>){
                executor.execute({->
                    if(response.code() == 200){
                        val user = response.body()!!
                        user.name = name

                        val count = dao.findUserByName(name)
                        if(count == null) {
                            dao.createUser(user)
                        }else{
                            dao.updateUser(user)
                        }

                        dataUser.postValue(Resource.success(user))
                    }

                })
            }

            override fun onFailure(call : Call<User>, throwable : Throwable){
                dataUser.postValue(Resource.error(throwable.message + "", null))
            }
        })
    }

    //Get the all subscriptions
    fun getSubscriptions(username : String) : LiveData<Resource<List<Subscription>>>? {
        val subs = dao.findSubscriptionsByUsername(username)
        subs.forEach {
            if(it.ownerId != null)
                it.owner = dao.findOwnerById(it.ownerId!!)
        }
        if(subs.size > 0)
            dataSubs.value = Resource.success(subs)
        else
            dataSubs.value = Resource.loading(null)

        refreshSubscriptions(username)

        return dataSubs
    }

    //Refresh the subscriptions' information
    private fun refreshSubscriptions(username : String){

        GithubService.createGithubServiceRepo(token)!!.getOwnSubscriptions(username).enqueue(object : Callback<List<Subscription>>{
            override fun onResponse(call : Call<List<Subscription>>, response : Response<List<Subscription>>){
                executor.execute({ ->
                    if(response.code() == 200) {
                        val subs = response.body()!!
                        subs.forEach {
                            try {
                                database.beginTransaction()
                                it.username = username
                                it.ownerId = it.owner?.id
                                val sub = dao.findSubscriptionById(it.id)
                                if (sub == null)
                                    dao.createSubscription(it)
                                else
                                    dao.updateSubscription(it)
                                updateOwner(it)
                                database.setTransactionSuccessful()
                            } finally {
                                database.endTransaction()
                            }
                        }

                        dataSubs.postValue(Resource.success(subs))
                    }
                })
            }

            fun updateOwner(it : Subscription){
                if(it.ownerId != null){
                    val owner = dao.findOwnerById(it.ownerId!!)
                    if(it.owner != null){
                        if(owner == null){
                            dao.createOwner(it.owner!!)
                        }else{
                            if(it.owner!!.id == owner.id){
                                dao.updateOwner(it.owner!!)
                            }else{
                                dao.deleteOwner(owner)
                                dao.createOwner(it.owner!!)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call : Call<List<Subscription>>, throwable : Throwable){
                dataSubs.postValue(Resource.error(throwable.message + "", null))
            }
        })

    }

}