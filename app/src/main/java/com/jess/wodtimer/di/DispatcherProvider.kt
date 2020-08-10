package com.jess.wodtimer.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * @author jess
 * @since 2020.06.12
 */
interface DispatcherProvider {
    val job: Job
    fun main(): CoroutineContext
    fun io(): CoroutineContext
    fun default(): CoroutineContext
}

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    override val job: Job = Job()
    override fun main(): CoroutineContext = Dispatchers.Main + job
    override fun io(): CoroutineContext = Dispatchers.IO + job
    override fun default(): CoroutineContext = Dispatchers.Default + job

}