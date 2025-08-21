package com.readboy.flowpractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readboy.flowpractice.db.DBHelper
import com.readboy.flowpractice.db.entity.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    fun insertUser(uid: String, name: String, age: String) {
        viewModelScope.launch{
            DBHelper.insertUser(User(uid.toInt(), name, age))
        }
    }
    fun getAllUser(): Flow<List<User>> {
        return DBHelper.queryAllUser()
            .catch { e -> e.printStackTrace() }
            .flowOn(Dispatchers.IO)
    }
}