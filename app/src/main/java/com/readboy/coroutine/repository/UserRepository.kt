package com.readboy.coroutine.repository

import com.readboy.coroutine.api.model.User
import com.readboy.coroutine.api.userApi

class UserRepository {
    suspend fun getUser(name: String): User {
        return userApi.getUserSuspend(name)
    }
}