package com.readboy.composeapp.ui.components.Layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.readboy.composeapp.R
import com.readboy.composeapp.model.Author

@Composable
fun MessageCard(author: Author) {
    Row(modifier = Modifier
        .padding(all = 8.dp)
        .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .clickable{}

        ) {
            Text(text = author.name)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = author.body,
                modifier = Modifier.padding(end = 16.dp),
            )
        }
    }
}