package com.readboy.coroutine

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
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

class ExampleUnitTest3 {
    @Test
    fun `test CoroutineContext`() = runBlocking<Unit> {
         // 这里能使用 + 是因为CoroutineContext对其进行了重载
         launch(Dispatchers.Default + CoroutineName("test")) {
             println("I'm working in thread: ${Thread.currentThread().name}")
        }
    }

    @Test
    fun `test CoroutineScope extend`() = runBlocking<Unit> {
        // 异常处理器
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        val scope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName("test") + coroutineExceptionHandler)
        val job = scope.launch {
            println("${coroutineContext[Job]} ${Thread.currentThread().name}")
            val res = async {
                println("${coroutineContext[Job]} ${Thread.currentThread().name}")
                "OK"
            }.await()
        }
        job.join()
    }

    @Test
    fun `test exception propagation root`() = runBlocking<Unit> {
        val job = launch {
            // 在第一时间捕获异常
            try {
                throw Exception("test")
            } catch (e: Exception) {
                println("Caught $e")
            }
        }
        job.join()

        val deffer = async {
            throw Exception("test")
        }
        try {
            // 在await中捕获异常
            deffer.await()
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    @Test
    fun `test exception propagation`() = runBlocking<Unit> {
        val scope = CoroutineScope(Job())
        val job = scope.launch {
            async {
                throw Exception("test")
            }
        }
        job.join()
    }

    @Test
    fun `test SupervisorJob`() = runBlocking<Unit> {
        val supervisor = CoroutineScope(SupervisorJob())
        val job1 = supervisor.launch {
            delay(100)
            println("child 1")
            throw Exception("test")
        }
        val job2 = supervisor.launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("child 2 is finished")
            }
        }
    }

    @Test
    fun `test CoroutineExceptionHandler`() = runBlocking<Unit> {
        // 异常处理器
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
//        val job = launch(coroutineExceptionHandler) {
//            throw Exception("job") // 可以被捕获
//        }
//        val deffer = async(coroutineExceptionHandler) {
//            throw Exception("deffer") // 不会被捕获，会直接抛出
//        }
//        job.join()
//        deffer.await()

//        val scope = CoroutineScope(Job())
//        val job = scope.launch(coroutineExceptionHandler) {
//            launch{
//                throw Exception("job") // 可以被捕获
//            }
//        }

        val scope = CoroutineScope(Job())
        val job = scope.launch {
            launch(coroutineExceptionHandler){
                throw Exception("job") // 不会被捕获
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun `test exception aggregation`() = runBlocking<Unit> {
        // 异常处理器
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception ${exception.suppressed.contentToString()}")
        }

        val job = GlobalScope.launch(coroutineExceptionHandler) {
            launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    throw ArithmeticException()
                }
            }
            launch {
                delay(100)
                throw Exception("test")
            }
        }
    }
}