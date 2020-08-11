package com.jess.wodtimer.common.extension

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * @author jess
 * @since 2020.08.11
 */

/**
 * 기본 예외 처리
 */
private val defaultExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
}


/**
 * 예외가 발생해도 크래시를 일으키지 않는 리시버의 child [코루틴 스코프][CoroutineScope]를 생성한다.
 */
fun CoroutineScope.safeScope() = CoroutineScope(this.coroutineContext + SupervisorJob() + defaultExceptionHandler)
