package com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig

import android.text.TextUtils
import com.example.kotlin.livedataroomretrofitkotlindemo.network.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Github remote service
 * Created by tianlu on 2017/11/22.
 */
class GithubService private constructor(){

    companion object service {

        var githubServiceLiveData : GithubLiveDataApi? = null
        var githubServiceRepository : GithubRepositoryApi? = null


        fun createGithubService(githubToken : String) : GithubLiveDataApi? {

            if(githubServiceLiveData == null){
                val builder = Retrofit.Builder()
                        .addCallAdapterFactory(LiveDataCallAdapterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.github.com")

                if(!TextUtils.isEmpty(githubToken)){

                    val client = OkHttpClient.Builder().addInterceptor({ chain ->
                        val request = chain.request()
                        val newReq = request.newBuilder()
                                .addHeader("Authorization", "token $githubToken")
                                .build()
                        chain.proceed(newReq)

                    }).build()

                    builder.client(client)
                }

                githubServiceLiveData = builder.build().create(GithubLiveDataApi::class.java)
            }

            return githubServiceLiveData
        }

        fun createGithubServiceRepo(githubToken : String) : GithubRepositoryApi? {

            if(githubServiceRepository == null){
                val builder = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://api.github.com")

                if(!TextUtils.isEmpty(githubToken)){

                    val client = OkHttpClient.Builder().addInterceptor({ chain ->
                        val request = chain.request()
                        val newReq = request.newBuilder()
                                .addHeader("Authorization", "token $githubToken")
                                .build()
                        chain.proceed(newReq)

                    }).build()

                    builder.client(client)
                }
                githubServiceRepository = builder.build().create(GithubRepositoryApi::class.java)
            }


            return githubServiceRepository
        }
    }
}