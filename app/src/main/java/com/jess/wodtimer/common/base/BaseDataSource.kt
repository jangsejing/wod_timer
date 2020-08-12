package com.jess.wodtimer.common.base

import com.jess.wodtimer.di.provider.DispatcherProvider

interface BaseDataSource {

    val dispatcher: DispatcherProvider?

    fun onCleared() {
        cancel()
    }

    fun cancel() {
        dispatcher?.job?.cancel()
    }
}