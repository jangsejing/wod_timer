package com.jess.wodtimer.common.extension

import android.widget.TextView
import com.jess.wodtimer.R
import java.util.*

/**
 * @author jess
 * @since 2020.08.11
 */

/**
 * 시간 형식
 *
 * @param timeMillis
 */
fun TextView.timeFormat(timeMillis: Long) {
    val minute = (timeMillis / 1000) / 60
    val seconds = (timeMillis / 1000) % 60
    this.text = String.format(
        Locale.getDefault(),
        context.getString(R.string.record_format, minute, seconds)
    )
}