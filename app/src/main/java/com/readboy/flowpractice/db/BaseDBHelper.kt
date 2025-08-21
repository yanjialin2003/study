package com.readboy.flowpractice.db

import androidx.room.Room
import com.readboy.flowpractice.BaseApplication


abstract class BaseDBHelper {
    internal val DB = Room.databaseBuilder(
        BaseApplication.instance,
        AppDataBase::class.java,
        "com.readboy.flowpractice"
    )
        .addMigrations(
//            Migrations.migration_1_2()
        )
        .allowMainThreadQueries()
        .build()
}