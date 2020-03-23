package com.example.coroutinesexample

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun helloWorld() = runBlocking {

        val intChannel = Channel<Int>(Channel.RENDEZVOUS).apply {
            invokeOnClose {
                println("channel closed")
            }
        }
        val flow = intChannel.receiveAsFlow()

        launch {
            var counter = 0

            repeat(10) {
                delay(100)
                intChannel.send(counter++)
            }

            println("stop sending")
        }

        launch {
            consumeToWorkers(flow)
        }

        val brokenWorkerJob = launch {
            brokenWorker(flow)
        }

        delay(200)
        brokenWorkerJob.cancel()


        println("the end")
    }

    suspend fun consumeToWorkers(flow: Flow<Int>) {
        flow.collect {
            println("current: $it")
            delay(50)
        }
    }

    suspend fun brokenWorker(flow: Flow<Int>) {
        flow.collect {
            println("current: ${it + 10}")
        }
    }

    @Test
    fun example2() = runBlocking {
        var invokeTime = 0
        val timer = repeatableTimer(5)

        launch {
            timer.collect {
                println("from 1")
                delay(100)
            }
        }

        launch {
            timer.collect {
                println("from 2")
            }
        }

        Unit


    }

    class A() {
        fun abc() {
            listOf(1, 2, 3).asFlow().onEach {
                MainScope()
            }.launchIn(CoroutineScope(Job()))
        }


    }


    fun CoroutineScope.repeatableTimer(time: Int): Flow<Unit> {
        val channel = BroadcastChannel<Unit>(Channel.CONFLATED)

        launch {
            var counter = 0

            while (counter++ < time) {
                channel.send(Unit)
                delay(100)
            }
            channel.close()
        }

        return channel.asFlow()
    }

    @Test
    fun example3() = runBlocking {
        launch {
            delay(60000)
        }
    }


}