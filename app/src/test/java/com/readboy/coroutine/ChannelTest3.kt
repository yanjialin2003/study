package com.readboy.coroutine

import com.readboy.coroutine.api.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.Test
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicInteger

class ChannelTest3 {

    @Test
    fun testNotSafeConcurrent() = runBlocking {
        var count = 0
        List(1000) {
            launch {
                count++
            }
        }.joinAll()
        println("count=$count") // 每次值都不一样且不为1000
    }

    @Test
    fun testSafeConcurrent() = runBlocking {
        var count = AtomicInteger(0) // 使其变成原子性的
        List(1000) {
            launch {
                count.incrementAndGet()
            }
        }.joinAll()
        println("count=${count.get()}")
    }

    @Test
    fun testSafeConcurrentTools() = runBlocking {
        var count = 0
//        val mutex = Mutex() // 轻量级锁
        val semaphore = Semaphore(1)
        List(1000) {
            launch {
//                mutex.withLock {
//                    count++
//                }
                semaphore.acquire(1)
                count++
                semaphore.release(1)
            }
        }.joinAll()
        println("count=$count")
    }

    @Test
    fun testOuter() = runBlocking {
        var count = 0
        val res = count + List(1000) {
            async { 1 }
        }.sumOf { it.await() }
        println("res=$res")
    }
}