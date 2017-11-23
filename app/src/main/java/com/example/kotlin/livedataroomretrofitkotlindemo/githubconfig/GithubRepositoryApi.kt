package com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig

import com.example.kotlin.livedataroomretrofitkotlindemo.network.Contributor
import com.example.kotlin.livedataroomretrofitkotlindemo.network.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Github remote api
 * Created by tianlu on 2017/11/22.
 */
interface GithubRepositoryApi {

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/repos/{owner}/{repo}/contributors")
    fun getContributors(@Path("owner") owner : String,
                     @Path("repo") repo : String) : Call<List<Contributor>>

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{user}")
    fun getUser(@Path("user") user : String) : Call<User>
}
