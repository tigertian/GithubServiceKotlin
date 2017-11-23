package com.example.kotlin.livedataroomretrofitkotlindemo.network


import android.text.TextUtils
import android.util.Log
import retrofit2.Response
import java.io.IOException

/**
 * Created by tianlu on 2017/11/22.
 */
class ApiResponse<T> {
    val code: Int
    val body: T?
    val errorMessage: String?

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Log.e("Parse", "error while parsing response")
                }

            }
            if (TextUtils.isEmpty(message)) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }

    }

    val isSuccessful: Boolean
        get() = code >= 200 && code < 300

}