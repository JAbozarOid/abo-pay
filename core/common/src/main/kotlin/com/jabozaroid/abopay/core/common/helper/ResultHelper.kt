package com.jabozaroid.abopay.core.common.helper

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


fun <T> transformResult(provider: (() -> T)): T {
    return try {
        provider.invoke()
    } catch (e: Exception) {
        throw e
    }
}

suspend fun <T> suspendTransformResult(provider: suspend (() -> T)): T {
    return try {
        provider.invoke()
    } catch (e: Exception) {
        throw e
    }
}


fun <T> transformApiResult(provider: (() -> T)): T {
    return try {
        provider.invoke()
    } catch (e: Exception) {
        throw e
    }
}

inline fun <T> executeSuspend(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline action: suspend () -> T
): T {
    return runBlocking { withContext(dispatcher) { action.invoke() } }
}

