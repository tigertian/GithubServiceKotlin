package com.example.kotlin.livedataroomretrofitkotlindemo.githubconfig

import android.text.TextUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.String.format

/**
 * Github remote service
 * Created by tianlu on 2017/11/22.
 */
class GuthubService private constructor(){

    companion object service {

        fun createGithubService(githubToken : String) : GithubApi {

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

            return builder.build().create(GithubApi::class.java)
        }
    }
}