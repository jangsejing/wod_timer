package com.jess.wodtimer.common.base

import androidx.lifecycle.LiveData
import com.jess.wodtimer.di.provider.DispatcherProvider

interface BaseDataSource {

    val dispatcher: DispatcherProvider?
    val isProgress: LiveData<Boolean>

    fun onCleared() {
        cancel()
    }

    fun cancel() {
        dispatcher?.job?.cancel()
    }
}