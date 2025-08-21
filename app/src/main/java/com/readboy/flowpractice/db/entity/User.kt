package com.readboy.flowpractice.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "age") var age: String,
)