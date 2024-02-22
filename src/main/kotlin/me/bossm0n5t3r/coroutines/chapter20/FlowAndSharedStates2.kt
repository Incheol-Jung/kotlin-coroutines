package me.bossm0n5t3r.coroutines.chapter20

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.random.Random

private fun Flow<*>.counter(): Flow<Int> {
    var counter = 0
    return this.map {
        counter++
        // to make it busy for a while
        List(100) { Random.nextLong() }.shuffled().sorted()
        counter
    }
}

suspend fun main(): Unit =
    coroutineScope {
        val f1 = List(1_000) { "$it" }.asFlow()
        val f2 =
            List(1_000) { "$it" }.asFlow()
                .counter()

        launch { println(f1.counter().last()) } // 1000
        launch { println(f1.counter().last()) } // 1000
        launch { println(f2.last()) } // less than 2000
        launch { println(f2.last()) } // less than 2000
    }
