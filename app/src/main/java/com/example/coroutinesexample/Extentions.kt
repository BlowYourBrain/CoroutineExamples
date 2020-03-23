package com.example.coroutinesexample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce

fun CoroutineScope.repeatableTimer(delay: Long, channel: ReceiveChannel<Boolean>): ReceiveChannel<Unit> = produce {

    suspend fun checkState(turnOn: Boolean, sendChannel: SendChannel<Unit>, channel: ReceiveChannel<Boolean>) {
        if (turnOn) {
            sendChannel.send(Unit)
        } else {
            val state = channel.receive()
            checkState(state, sendChannel, channel)
        }
    }

    send(Unit)
    while (true) {
        kotlinx.coroutines.delay(delay)

        channel.poll()?.let { state ->
            checkState(state, this, channel)
        } ?: run {
            send(Unit)
        }
    }

}