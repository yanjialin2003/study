package com.readboy.composeapp.ui.components.Layout

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutDemo1() {
    ConstraintLayout {
        // 通过createRefs创建(两)个引用
        val (constraint1, constraint2) = createRefs()
        Button(
            onClick = {},
            // constrainAs(引用){ 约束条件 }
            modifier = Modifier.constrainAs(constraint1) {
                // A.linkTo(B) 表示A链接到B
                top.linkTo(parent.top)
            }
        ) {
            Text(text = "Constraint1")
        }
        Text(
            text = "Constraint2",
            modifier = Modifier.constrainAs(constraint2) {
                top.linkTo(constraint1.bottom)
                start.linkTo(constraint1.start)
                end.linkTo(constraint1.end)
            }
        )
    }
}

@Composable
fun ConstraintLayoutDemo2() {
    ConstraintLayout {
        val (constraint1, constraint2, constraint3) = createRefs()
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(constraint1) {
                top.linkTo(parent.top)
            }
        ) {
            Text(text = "Constraint1")
        }
        Text(
            text = "Constraint2",
            modifier = Modifier.constrainAs(constraint2) {
                top.linkTo(constraint1.bottom)
                centerAround(constraint1.end)
            }
        )

        // 将constraint1和constraint2组合，建立一个barrier
        val barrier = createEndBarrier(constraint1, constraint2)
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(constraint3) {
                top.linkTo(parent.top)
                start.linkTo(barrier)
            }
        ) {
            Text(text = "Constraint3")
        }
    }
}

@Composable
fun ConstraintLayoutDemo3() {
    ConstraintLayout {
        val constraint1 = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            text = "very very very very very very very very very very long text",
            modifier = Modifier
                .constrainAs(constraint1) {
                top.linkTo(parent.top)
                linkTo(start = guideline, end = parent.end)
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConstraintLayoutDemo4() {
    val boolean = true
    val constraints = if(boolean) {
        decoupleConstraints(16.dp)
    } else {
        decoupleConstraints(32.dp)
    }
    ConstraintLayout(constraints) {
        Button(
            onClick = {},
            modifier = Modifier.layoutId("constraint1")
        ) {
            Text(text = "Constraint1")
        }
        Text(
            text = "Constraint2",
            modifier = Modifier.layoutId("constraint2")
        )
    }
}


private fun decoupleConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val btn = createRefFor("constraint1")
        val text = createRefFor("constraint2")
        constrain(btn) {
            top.linkTo(parent.top, margin = margin)
        }
        constrain(text) {
            top.linkTo(btn.bottom, margin = margin)
            start.linkTo(btn.start)
            end.linkTo(btn.end)
        }
    }
}