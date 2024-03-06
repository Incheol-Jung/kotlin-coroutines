package me.bossm0n5t3r.coroutines.chapter23

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun main() {
    flowOf('a', 'b')
        .map { it.uppercase() }
        .collect { print(it) } // AB
}

private fun <T, R> Flow<T>.map(transform: suspend (value: T) -> R): Flow<R> =
    flow {
        collect { value ->
            emit(transform(value))
        }
    }

private fun <T> flowOf(vararg elements: T): Flow<T> =
    flow {
        for (element in elements) {
            emit(element)
        }
    }
