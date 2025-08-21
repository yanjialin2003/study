package com.readboy.coroutine

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
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
import org.junit.Test

class ChannelTest {
    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun testChannel() = runBlocking {
        val channel = Channel<Int>()

        // 生产者
        val provider = GlobalScope.launch {
            var i = 1
            while (true) {
                delay(1000)
                channel.send(i++)
                println("Send $i")
            }
        }

        // 消费者
        val consumer = GlobalScope.launch {
            while (true) {
                delay(2000) // 消费时间 > 生产时间，当缓存区满了时，生产的send会挂起
                val element = channel.receive()
                println("Receive $element")
            }
        }

        joinAll(provider, consumer)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun testIteratorChannel() = runBlocking {
        val channel = Channel<Int>(Channel.UNLIMITED) // 设置缓存区大小为无限大

        // 生产者
        val provider = GlobalScope.launch {
            for(x in 1..5){
                channel.send(x * x)
                println("Send ${x * x}")
            }
        }

        // 消费者
        val consumer = GlobalScope.launch {
//            val iterator = channel.iterator()
//            while (iterator.hasNext()){
//                val element = iterator.next()
//                println("Receive $element")
//                delay(2000)
//            }

            for(element in channel){
                println("Receive $element")
                delay(2000)
            }
        }

        joinAll(provider, consumer)
    }

    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class,)
    @Test
    fun testFastProviderChannel() = runBlocking {
        val receiveChannel: ReceiveChannel<Int> = GlobalScope.produce {
            repeat(100) {
                delay(1000)
                send(it)
            }
        }

        // 消费者
        val consumer = GlobalScope.launch {
            for(element in receiveChannel){
                println("Receive $element")
                delay(2000)
            }
        }
        consumer.join()
    }
    @OptIn(DelicateCoroutinesApi::class, ObsoleteCoroutinesApi::class)
    @Test
    fun testFastConsumerChannel() = runBlocking {
        val consumerChannel: SendChannel<Int> = GlobalScope.actor {
            while (true) {
                val element = receive()
                println("Receive $element")
            }
        }

        val provider = GlobalScope.launch {
            for(x in 1..5){
                consumerChannel.send(x * x)
                println("Send ${x * x}")
            }
        }
        provider.join()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun testCloseChannel() = runBlocking {
        val channel = Channel<Int>(3)

        // 生产者
        val provider = GlobalScope.launch {
            List(3){
                channel.send(it)
                println("Send $it")
            }
            channel.close()
            println("Channel is closed, Close for send: ${channel.isClosedForSend}, Close for receive: ${channel.isClosedForReceive}")
        }

        // 消费者
        val consumer = GlobalScope.launch {
            for(element in channel){
                println("Receive $element")
                delay(1000)
            }
            println("After Channel, Close for send: ${channel.isClosedForSend}, Close for receive: ${channel.isClosedForReceive}")
        }

        joinAll(provider, consumer)
    }
}