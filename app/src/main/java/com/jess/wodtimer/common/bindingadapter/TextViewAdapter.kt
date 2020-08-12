package com.jess.wodtimer.common.bindingadapter

import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.jess.wodtimer.R
import java.util.*

/**
 * @author jess
 * @since 2020.08.12
 */
object TextViewAdapter {

    /**
     * 텍스트 컬러
     *
     * @param color
     */
    @JvmStatic
    @BindingAdapter("textColor")
    fun setTextColor(
        view: TextView,
        @ColorInt color: Int
    ) {
        view.setTextColor(color)
    }

    /**
     * 시간 형식
     *
     * @param timeMillis
     */
    @JvmStatic
    @BindingAdapter("timeFormat")
    fun TextView.timeFormat(timeMillis: Long) {
        val minute = (timeMillis / 1000) / 60
        val seconds = (timeMillis / 1000) % 60
        this.text = String.format(
            Locale.getDefault(),
            context.getString(R.string.record_format, minute, seconds)
        )
    }
}