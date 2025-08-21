package com.readboy.composeapp.viewmodel

import com.readboy.composeapp.model.Author

object SampleData {
    val conversationSample = listOf(
        Author("John", "Hello, how are you?"),
        Author("Jane", "I'm good, thanks! How about you?"),
        Author("John", "I'm doing well, thanks for asking!"),
        Author("Jane", "That's great to hear! What have you been up to lately?")
    )
}