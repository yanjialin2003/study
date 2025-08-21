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
import org.junit.Test

class ChannelTest2 {

    @Test
    fun testSelectChannel() = runBlocking {
        val channels = listOf(Channel<Int>(), Channel<Int>())

        val job1 = launch {
            delay(100)
            channels[0].send(100)
        }
        val job2 = launch {
            delay(50)
            channels[1].send(50)
        }

        joinAll(job1, job2)

        val res = select<Int> {
            channels.forEach {
                it.onReceive { it }
            }
        }
        println(res) // 50
    }

    @Test
    fun testSelectClause0() = runBlocking {
        val job1 = launch {
            delay(100)
            println("job1")
        }
        val job2 = launch {
            delay(50)
            println("job2")
        }

        select<Unit> {
            job1.onJoin { println("Job1 on join") }
            job2.onJoin { println("Job2 on join") }
        }
        delay(1000)
    }
}