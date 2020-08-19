package com.jess.wodtimer.common.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    /**
     * 시간 포맷
     *
     * @return
     */
    fun getFormat(
        format: String = "yyyy.MM.dd E",
        locale: Locale = Locale.getDefault()
    ): String? {
        return SimpleDateFormat(format, locale).format(Date())
    }
}