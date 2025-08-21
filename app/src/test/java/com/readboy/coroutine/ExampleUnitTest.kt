package com.readboy.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

import kotlin.system.measureTimeMillis

class ExampleUnitTest {
    @Test
    fun `test coroutine builder`() = runBlocking{
        // 返回一个Job对象，无返回结果
        val job1 = launch {
            delay(200)
            println("job1 finished")
        }
        // 返回一个Deferred对象(也是一个Job)，有返回结果
        val job2 = async {
            delay(200)
            println("job2 finished")
            "job2 result"
        }
        println(job2.await())
    }

    // job1执行完后才执行job2、job3
    @Test
    fun `test coroutine join`() = runBlocking{
        val job1 = launch {
            delay(2000)
            println("job1 finished")
        }
        job1.join()
        val job2 = launch {
            delay(200)
            println("job2 finished")
        }
        val job3 = launch {
            delay(200)
            println("job3 finished")
        }
    }

    @Test
    fun `test coroutine await`() = runBlocking{
        val job1 = async {
            delay(2000)
            println("job1 finished")
        }
        job1.await()
        val job2 = async {
            delay(200)
            println("job2 finished")
        }
        val job3 = async {
            delay(200)
            println("job3 finished")
        }
    }

    @Test
    fun `test sync`() = runBlocking{
        val time = measureTimeMillis {
            val one = doOne()
            val two = doTwo()
            println("result: ${one + two}")
        }
        println("cost time: $time ms") // 2026ms
    }
    @Test
    fun `test async`() = runBlocking{
        val time = measureTimeMillis {
            val one = async { doOne() }
            val two = async { doTwo() }
            println("result: ${one.await() + two.await()}")
        }
        println("cost time: $time ms") // 1007ms
    }
    private suspend fun doOne(): Int{
        delay(1000)
        return 1
    }
    private suspend fun doTwo(): Int{
        delay(1000)
        return 2
    }

    @Test
    fun `test start mode`() = runBlocking{
//        val job1 = launch(start = CoroutineStart.DEFAULT) {
//            delay(5000)
//            println("job1 finished")
//        }
//        delay(2000)
//        job1.cancel() // 未完成前取消

//        val job2 = launch(start = CoroutineStart.ATOMIC) {
//            // 一系列任务...
//            println("job2 prepare")
//            delay(5000)
//            println("job2 finished")
//        }
//        delay(2000)
//        job2.cancel() // 未完成前取消，只有执行到delay(5000)这里时才会取消，前面的任务不影响

//        val job3 = async(start = CoroutineStart.LAZY) {
//            delay(5000)
//            29
//        }
//        job3.cancel() // 要是在未await之前取消，则会抛出异常结束状态
//        println("result: ${job3.await()}")

        val job4 = async(context = Dispatchers.IO, start = CoroutineStart.UNDISPATCHED) {
            println("thread: ${Thread.currentThread().name}") // 这里显示是主线程（因为当前函数调用栈为主线程的特性）
        }
    }

    @Test
    fun `test coroutine scope builder`() = runBlocking{
        // 其中的一个子协程失败了，后续未执行完的所有的兄弟协程都会失败
        coroutineScope {
            val job1 = launch {
                delay(400)
                println("job1 finished")
            }
            val job2 = async {
                delay(200)
                println("job2 finished")
                "job2 result"
                // 这里job2完成了，但是抛出了异常错误，后续job1未完成被取消
                throw Exception("job2 error")
            }
        }

        // 其中的一个子协程失败了，后续未执行完的所有的兄弟协程都不会失败
        supervisorScope {
            val job1 = launch {
                delay(400)
                println("job1 finished")
            }
            val job2 = async {
                delay(200)
                println("job2 finished")
                "job2 result"
                // 这里job2完成了，但是抛出了异常错误，后续job1未完成不会被取消，且该错误未被捕获
                throw Exception("job2 error")
            }
        }
    }

    // 自定义协程作用域
    @Test
    fun `test custom coroutine scope`() = runBlocking{
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            delay(1000)
            println("job1 finished")
        }
        scope.launch {
            delay(1000)
            println("job2 finished")
        }
        delay(100)
        scope.cancel()
    }
}