package com.jess.wodtimer.common.util

import timber.log.Timber

inline fun tryCatch(action: () -> Unit) {
    try {
        action()
    } catch (e: Exception) {
        Timber.e(e)
    }
}