package me.bossm0n5t3r.coroutines.chapter22

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

suspend fun main() {
    flowOf(1, 2)
        .onEach { delay(1000) }
        .onStart { emit(0) }
        .collect { println(it) }
}

// 0
// (1 sec)
// 1
// (1 sec)
// 2
