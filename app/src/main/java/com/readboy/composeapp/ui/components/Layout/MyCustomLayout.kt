package com.readboy.composeapp.ui.components.Layout

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MyCustom() {
    Row {
        Text(
            text = "Padding",
            modifier = Modifier
                .padding(top = 24.dp)
                .background(Color.Red)
        )
        Text(
            text = "NewPadding",
            modifier = Modifier
                .newPaddingTop(24.dp)
                .background(Color.Red)
        )
    }
}

// 新的PaddingTop是从基线开始
@SuppressLint("SuspiciousModifierThen")
fun Modifier.newPaddingTop(
    firstBaseLineToTop: Dp
) = this.then(
    // layout修饰符来修改元素位置
    layout { measurable, constraints ->
        // 测量子元素
        val placeable = measurable.measure(constraints)

        val firstBaseLine = placeable[FirstBaseline] // 获取元素基线值
        val placeableY = firstBaseLineToTop.roundToPx() - firstBaseLine // 计算新的Y坐标

        // 计算宽高
        val placeableWidth = placeable.width
        val placeableHeight = placeable.height + placeableY
        layout(placeableWidth, placeableHeight) {
            // 将元素放置在新的位置
            placeable.placeRelative(0, placeableY)
        }
    }
)


@Composable
fun MyColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        var yPosition = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeable ->
                placeable.placeRelative(0, yPosition)
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun MyColumnPreview() {
    MyColumn(
        modifier = Modifier
            .padding(16.dp)
    ){
        Text(text = "Hello", modifier = Modifier.background(Color.Red))
        Text(text = "World", modifier = Modifier.background(Color.Red))
    }
}

@Composable
fun TwoTexts1(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = "Hello",
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
        )
        VerticalDivider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            text = "World",
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Composable
fun TwoTexts2(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            // IntrinsicSize.Min获取到子元素的最小高度
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "Hello",
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start)
        )
        VerticalDivider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            text = "World",
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.End)
        )
    }
}
