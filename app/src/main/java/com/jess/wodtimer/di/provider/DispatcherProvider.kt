package com.jess.wodtimer.di.provider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * @author jess
 * @since 2020.08.11
 */
interface DispatcherProvider {
    var job: Job
    val main: CoroutineContext
    val io: CoroutineContext
    val default: CoroutineContext
    fun createJob()
}

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    override var job: Job = Job()
    override val main: CoroutineContext get() = Dispatchers.Main + job
    override val io: CoroutineContext get() = Dispatchers.IO + job
    override val default: CoroutineContext get() = Dispatchers.Default + job

    override fun createJob() {
        job = Job()
    }

}