package com.readboy.coroutine.api

import android.util.Log
import com.readboy.coroutine.api.model.User
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val userApi: UserApi by lazy {
    val retrofit = retrofit2.Retrofit.Builder()
        .client(OkHttpClient.Builder().addInterceptor {
            it.proceed(it.request()).apply {
                Log.d("UserApi", "response: ${code()}")
            }
        }.build())
        .baseUrl("http://192.168.1.4:8080/ko...") // 这里没用
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    retrofit.create(UserApi::class.java)
}

interface UserApi {
    @GET("user")
    fun getUser(@Query("name") name: String): Call<User>

    @GET("user")
    suspend fun getUserSuspend(@Query("name") name: String): User
}