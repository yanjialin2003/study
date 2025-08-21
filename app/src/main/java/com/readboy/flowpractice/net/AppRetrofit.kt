package com.readboy.flowpractice.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppRetrofit {
    private val URL = ""

    private val instance: Retrofit by lazy {
        Retrofit.Builder()
            .client(OkHttpClientHelper.getOkHttpClient())
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createRequest(request_interface: Class<T>): T {
        return instance.create(request_interface)
    }
}