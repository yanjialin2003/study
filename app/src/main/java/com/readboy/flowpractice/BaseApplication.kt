package com.readboy.flowpractice

import android.app.Application

class BaseApplication: Application() {
    companion object {
        lateinit var instance: BaseApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}