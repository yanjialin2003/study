package com.readboy.composeapp.ui.components.Layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.readboy.composeapp.model.Author

@Composable
fun ConversationLazy(messages: List<Author>) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(messages.size) { index ->
            MessageCard(messages[index])
        }
    }
}

@Composable
fun Conversation(messages: List<Author>) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        repeat(messages.size) { index ->
            MessageCard(messages[index])
        }
    }
}