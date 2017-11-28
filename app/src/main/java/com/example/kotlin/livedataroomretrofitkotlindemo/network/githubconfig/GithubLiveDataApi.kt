package com.example.kotlin.livedataroomretrofitkotlindemo.network.githubconfig

import android.arch.lifecycle.LiveData
import com.example.kotlin.livedataroomretrofitkotlindemo.network.ApiResponse
import com.example.kotlin.livedataroomretrofitkotlindemo.network.Contributor
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

/**
 * Github remote api
 * Created by tianlu on 2017/11/22.
 */
interface GithubLiveDataApi{

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributors(@Path("owner") owner : String,
                     @Path("repo") repo : String) : LiveData<ApiResponse<List<Contributor>>>

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{user}")
    fun getUser(@Path("user") user : String) : LiveData<ApiResponse<User>>

    /**
     * See https://developer.github.com/v3/users/#update-the-authenticated-user
     */
    @PATCH("/user")
    fun updateUser(@Body user : User) : LiveData<ApiResponse<User>>
}
