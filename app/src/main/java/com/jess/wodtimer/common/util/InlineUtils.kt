package com.jess.wodtimer.common.util

import timber.log.Timber

inline fun tryCatch(action: () -> Unit) {
    try {
        action()
    } catch (e: Exception) {
        Timber.e(e)
    }
}

inline fun <T : R, R> T.cast(
    clazz: Class<R>,
    block: R.() -> Unit
): R {
    if (clazz.isInstance(this)) (this as R).block()
    return this
}