package com.readboy.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class FlowTest {
    val flow1 = flow<Int>{
        for (i in 1..3) {
            delay(1000) // 一系列耗时操作(大量计算)
            emit(i) // 发射(产生)下一个值
        }
    }
    val flow2 = flowOf(1, 2, 3).onEach { delay(1000) }
    val flow3 = (1..3).asFlow().onEach { delay(1000) }

    @Test
    fun `test multiple values`() = runBlocking<Unit> {
        // flow1.collect { println(it) }
        // flow2.collect { println(it) }
        flow3.collect { println(it) }
    }

    val flow4 = flow<Int>{
        println("Flow Start ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }
    val flow5 = flow<Int>{
        withContext(Dispatchers.Default) {
            println("Flow Start ${Thread.currentThread().name}")
            for (i in 1..3) {
                delay(1000)
                emit(i)
            }
        }
    }
    val flow6 = flow<Int>{
        println("Flow Start ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }.flowOn(Dispatchers.Default)

    @Test
    fun `test context`() = runBlocking<Unit> {
        flow6.collect {
            println("${Thread.currentThread().name}: $it")
        }
    }

    val event = flow<Int>{
        println("Flow Start ${Thread.currentThread().name}")
        for (i in 1..3) {
            delay(1000)
            emit(i)
        }
    }.flowOn(Dispatchers.Default)

    @Test
    fun `test flow launch`() = runBlocking<Unit> {
        val job = event
            .onEach { event ->
                println("Event $event ${Thread.currentThread().name}")
            }
            // 在指定的协程中执行任务
            .launchIn(CoroutineScope(Dispatchers.IO))
        delay(2000)
        job.cancelAndJoin()
    }

    val flow7 = flow<Int>{
        for (i in 1..5) {
            println("emitting: $i")
            emit(i)
        }
    }

    @Test
    fun `test cancel flow check`() = runBlocking<Unit> {
//        flow7.collect {
//            println("collect: $it")
//            if(it == 3) cancel()
//        }
        (1..5).asFlow().cancellable().collect {
            println("collect: $it")
            if(it == 3) cancel()
        }
    }

    val flow8 = flow<Int>{
        for (i in 1..3) {
            println("emitting: $i")
            delay(100) // 生产每个元素只要100ms
            emit(i)
        }
    }

    @Test
    fun `test flow back pressure`() = runBlocking<Unit> {
        val time = measureTimeMillis {
            flow8
                // .flowOn(Dispatchers.Default) // 1026ms (100 + 300*3)
                // .buffer(50) // 缓冲50个 -> 1026ms (100 + 300*3)
                // .conflate() // 跳过中间一些值 -> 716ms (100 + 300*2)
                // .collectLatest { // 取消之前的任务，只保留最后一个任务 -> 785ms
                .collect {
                    delay(300) // 处理每个元素需要300ms
                    println("collect: $it")
                }
        }
        println("Time: $time") // 1226ms (100*3 + 300*3)
    }
}