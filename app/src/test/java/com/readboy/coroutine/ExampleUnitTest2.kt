package com.readboy.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import kotlinx.coroutines.yield

class ExampleUnitTest2 {
    @Test
    fun `test brother cancel`() = runBlocking{
        val scope = CoroutineScope(Dispatchers.Default)
        val job1 = scope.launch {
            delay(1000)
            println("job1 finished")
        }
        val job2 = scope.launch {
            delay(1000)
            println("job2 finished")
        }
        delay(100)
        job1.cancel()
        delay(1000)
    }

    @Test
    fun `test cancel cpu task by isActive`() = runBlocking{
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5 && isActive){
                if(System.currentTimeMillis() >= nextPrintTime){
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test cancel cpu task by ensureActive`() = runBlocking{
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5){
                ensureActive()
                if(System.currentTimeMillis() >= nextPrintTime){
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test cancel cpu task by yield`() = runBlocking{
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5){
                yield()
                if(System.currentTimeMillis() >= nextPrintTime){
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test release resources`() = runBlocking{
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally{
                println("job: I'm running finally")
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test cancel with NonCancellable`() = runBlocking{
        val job = launch{
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                // 强制无法取消
                withContext(NonCancellable){
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    @Test
    fun `test deal with timeout`() = runBlocking{
        withTimeout(1300){
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

    @Test
    fun `test deal with timeout return null`() = runBlocking{
        val res = withTimeoutOrNull(1300){
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
            "Done"
        } ?: "Timeout"
        println("res: $res")
    }
}