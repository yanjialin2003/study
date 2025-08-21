package com.readboy.coroutine

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.Test



class FlowTest2 {
    suspend fun performRequest(request: Int): String {
        delay(1000)
        return "response $request"
    }

    @Test
    fun `test transform flow operator`() = runBlocking<Unit> {
        (1..3).asFlow()
            .map { performRequest(it) }
            .collect { println(it) }

        (1..3).asFlow()
            .transform {
                emit("Making request $it")
                emit(performRequest(it))
            }
            .collect { println(it) }
    }

    val flow = flow<Int>{
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally block")
        }
    }

    @Test
    fun `test limit length operator`() = runBlocking<Unit> {
        flow
            .take(2)
            .collect { println(it) }
    }

    @Test
    fun `test terminal operator`() = runBlocking<Unit> {
        val sum = (1..5).asFlow()
            .map { it * it }
            .reduce { a, b -> a + b }
        println(sum)
    }

    @Test
    fun `test zip`() = runBlocking<Unit> {
        val nums = (1..3).asFlow()
        val strs = flowOf("one", "two", "three")
        nums
            .zip(strs) { a, b -> "$a -> $b" }
            .collect { println(it) }
    }

    @Test
    fun `test zip time`() = runBlocking<Unit> {
        val nums = (1..3).asFlow().onEach { delay(300) }
        val strs = flowOf("one", "two", "three").onEach { delay(400) }
        val startTime = System.currentTimeMillis()
        nums
            .zip(strs) { a, b -> "$a -> $b" }
            .collect {
                println("$it at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }

    fun requestFlow(i: Int) = flow<String> {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testFlatMapConcat() = runBlocking<Unit> {
        val startTime = System.currentTimeMillis()
        (1..3).asFlow()
            .onEach { delay(100) }
            // .map { requestFlow(it) } // 得到的是Flow<Flow<String>>类型
            // .flatMapConcat { requestFlow(it) } // 连接模式展平(1: First -> 1: Second -> 2: First -> 2: Second -> 3: First -> 3: Second)
            // .flatMapMerge { requestFlow(it) } // 合并模式展平(1: First -> 2: First -> 3: First -> 1: Second -> 2: Second -> 3: Second)
            .flatMapLatest { requestFlow(it) } // 最新模式展平(1: First -> 1: Second -> 2: First -> 2: Second -> 3: First -> 3: Second)
            .collect {
                println("$it at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }
}