package com.readboy.flowpractice.db

import com.readboy.flowpractice.db.entity.*
import kotlinx.coroutines.flow.Flow

object DBHelper: BaseDBHelper() {
    /**
     * 插入用户信息
     */
    suspend fun insertUser(user: User){
        DB.UserDao().insertUser(user)
    }

    /**
     * 查询用户信息
     */
    fun queryAllUser(): Flow<List<User>> {
        return DB.UserDao().queryAllUser()
    }
}