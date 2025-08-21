package com.readboy.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking
import org.junit.Test



class FlowTest3 {

    val flow = flow<Int>{
        for (i in 1..3){
            println("Emitting $i")
            emit(i)
        }
    }

    @Test
    fun testFlatExceptionDown() = runBlocking<Unit> {
        // 下游的异常通过try catch命令式捕获
        try {
            flow.collect {
                println(it)
                check(it <= 1) { "Collected $it" }
            }
        } catch (e: Throwable) {
            println("Caught $e")
        }
    }

    @Test
    fun testFlatExceptionUp() = runBlocking<Unit> {
        // 上游的异常通过.catch声明式捕获
        flow{
            throw RuntimeException("Error")
            emit(1)
        }.catch {
            println("Caught $it")
            emit(10) // 可以恢复
        }.flowOn(Dispatchers.IO).collect { println(it) }
    }

    @Test
    fun testFlowCompleteInFinally() = runBlocking<Unit> {
        try {
            flow.collect { println(it) }
        } finally {
            println("Done")
        }
    }

    val flow2 = flow<Int>{
        for (i in 1..3){
            throw RuntimeException("Error")
            emit(i)
        }
    }

    @Test
    fun testFlowCompleteInOnCompletion() = runBlocking<Unit> {
        flow.onCompletion{
            println("Done")
        }.collect {
            println(it)
        }
        // 既可以获得上游的异常信息(不是捕获)
        flow2.onCompletion { exception ->
            if (exception != null) println("Caught $exception")
        }.collect {
            println(it)
        }
        // 也可以获得下游的异常信息(不是捕获)
        flow.onCompletion{ exception ->
            if (exception != null) println("Caught $exception")
        }.collect {
            println(it)
            check(it <= 1) { "Collected $it" }
        }
    }


}