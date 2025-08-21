package com.readboy.flowpractice.net

import okhttp3.OkHttpClient

object OkHttpClientHelper {
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}
