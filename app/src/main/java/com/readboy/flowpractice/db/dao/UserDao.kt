package com.readboy.flowpractice.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.readboy.flowpractice.db.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * 插入用户信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /**
     * 查询所有用户信息
     */
    @Query("select * from user")
    fun queryAllUser(): Flow<List<User>>
}