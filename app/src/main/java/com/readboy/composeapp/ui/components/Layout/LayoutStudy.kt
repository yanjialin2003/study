package com.readboy.composeapp.ui.components.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LayoutStudy() {
    Scaffold(
        topBar = { TopBar(
            title = { Text("Layout Study") },
            actions = { IconButton(onClick = {}) { Icon(Icons.Filled.Favorite, contentDescription = "Favorite") } }
        ) }
    ) { innerPadding ->
        BodyContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text("Hello, World!")
        Text("Hello, Android!")
    }
}

@Composable
fun TopBar(
    title: @Composable () -> Unit = { Text("Title") },
    actions: @Composable () -> Unit = { }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start =  10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        title()
        actions()
    }
}