package com.readboy.composeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.readboy.composeapp.ui.components.Layout.ConversationLazy
import com.readboy.composeapp.ui.theme.ComposeAppTheme
import com.readboy.composeapp.viewmodel.SampleData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                ConversationLazy(SampleData.conversationSample)
            }
        }
    }
}
