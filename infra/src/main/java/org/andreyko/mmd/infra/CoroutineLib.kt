package org.andreyko.mmd.infra

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.startCoroutine

inline fun <reified T> sync(noinline future: suspend () -> T): T {

    val cont = object : Continuation<T> {
        val sync = Object()
        var completed = false
        var result: T? = null
        var error: Throwable? = null

        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resume(value: T) {
            synchronized(sync) {
                if(completed){
                    throw IllegalStateException("already has been completed")
                }
                result = value
                completed = true
                sync.notifyAll()
            }
        }

        override fun resumeWithException(exception: Throwable) {
            synchronized(sync) {
                if(completed){
                    throw IllegalStateException("already has been completed")
                }
                error = exception
                completed = true
                sync.notifyAll()
            }
        }

        fun block(): T {
            synchronized(sync) {
                while (!completed) {
                    sync.wait()
                }
            }
            error?.let{
                throw it
            }
            return result!!
        }
    }
    future.startCoroutine(cont)
    return cont.block()
}