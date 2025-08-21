package com.readboy.coroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readboy.coroutine.api.model.User
import com.readboy.coroutine.repository.UserRepository
import kotlinx.coroutines.launch

class ExampleViewModel(): ViewModel() {
    private val userRepository = UserRepository()

    val userLiveData = MutableLiveData<User>()

    fun getUser(name: String) {
        viewModelScope.launch {
            userLiveData.value = userRepository.getUser(name)
        }
    }
}