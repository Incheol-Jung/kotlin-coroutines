package me.bossm0n5t3r.coroutines.chapter16

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope

@OptIn(ExperimentalCoroutinesApi::class)
private fun CoroutineScope.numbers(): ReceiveChannel<Int> =
    produce {
        repeat(3) { num ->
            send(num + 1)
        }
    }

@OptIn(ExperimentalCoroutinesApi::class)
private fun CoroutineScope.square(numbers: ReceiveChannel<Int>) =
    produce {
        for (num in numbers) {
            send(num * num)
        }
    }

suspend fun main() =
    coroutineScope {
        val numbers = numbers()
        val squared = square(numbers)
        for (num in squared) {
            println(num)
        }
    }

// 1
// 4
// 9
