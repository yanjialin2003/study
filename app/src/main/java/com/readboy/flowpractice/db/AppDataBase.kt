package com.readboy.flowpractice.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.readboy.flowpractice.db.dao.UserDao
import com.readboy.flowpractice.db.entity.User

@Database(
    entities = [
        User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {
     abstract fun UserDao(): UserDao
}