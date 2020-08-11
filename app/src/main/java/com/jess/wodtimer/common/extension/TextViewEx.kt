package com.jess.wodtimer.common.extension

import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.jess.wodtimer.R
import com.jess.wodtimer.common.util.tryCatch
import java.util.*

/**
 * @author jess
 * @since 2020.08.11
 */

/**
 * <b>, </b> 강조 텍스트 변환
 */
@BindingAdapter("original", "start", "end", "color")
fun TextView.accentTextColor(
    original: String?,
    start: String?,
    end: String?,
    @ColorInt color: Int
) {

    if (original.isNullOrEmpty() || start.isNullOrEmpty() || end.isNullOrEmpty()) {
        return
    }

    tryCatch {
        val startIndex = original.indexOf(start)
        val endIndex = original.indexOf(end)
        if (startIndex > -1 && endIndex > -1) {
            // start, end 사이 문자 추출
            val output = original.substring(startIndex + start.length, endIndex)
            val replace = original.replace(start, "").replace(end, "")
            val accentStart = replace.indexOf(output)

            val convert = SpannableString(replace).apply {
                setSpan(
                    ForegroundColorSpan(color),
                    accentStart,
                    accentStart + output.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
            }
            this.text = convert
        } else {
            this.text = original
        }
        return
    }
    this.text = original
}

/**
 * oldValue를 newValue로 변경
 */
@BindingAdapter("original", "oldValue", "newValue")
fun TextView.replaceText(
    original: String?,
    oldValue: String?,
    newValue: String?
) {

    if (original.isNullOrEmpty() || oldValue.isNullOrEmpty() || newValue.isNullOrEmpty()) {
        return
    }

    tryCatch {
        var convert: String = original
        oldValue
            .split(",")
            .filter { it.isNotBlank() }
            .map {
                convert = convert.replace(it, newValue)
            }
            .toString()
        this.text = convert
        return
    }
    this.text = original
}

/**
 * 시간 형식
 *
 * @param timeMillis
 */
@BindingAdapter("timeFormat")
fun TextView.timeFormat(timeMillis: Long) {
    val minute = (timeMillis / 1000) / 60
    val seconds = (timeMillis / 1000) % 60
    this.text = String.format(
        Locale.getDefault(),
        context.getString(R.string.record_format, minute, seconds)
    )
}