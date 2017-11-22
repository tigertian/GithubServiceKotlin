package com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig

import android.arch.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Github remote api
 * Created by tianlu on 2017/11/22.
 */
interface GithubApi{

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributorsAsLiveData(@Path("owner") owner : String,
                     @Path("repo") repo : String) : LiveData<List<Contributor>>

    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributors(@Path("owner") owner : String,
                     @Path("repo") repo : String) : List<Contributor>

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{user}")
    fun getUserAsLiveData(@Path("user") user : String) : LiveData<String>

    @GET("/users/{user}")
    fun getUser(@Path("user") user : String) : String
}

data class Contributor(var login : String,
                       var contributions : Long)