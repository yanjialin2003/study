package com.readboy.mycustomview.jetpack.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _welcomeMessage = MutableLiveData<String>()
    val welcomeMessage: LiveData<String> = _welcomeMessage
    private var clickCount = 0

    init {
        _welcomeMessage.value = "Hello World!"
    }

    fun updateMessage() {
        clickCount++
        _welcomeMessage.value = "Click $clickCount"
    }
}