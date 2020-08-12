package com.jess.wodtimer.common.util

import android.content.Context

/**
 * @since jess
 * @author 2020.08.12
 */
object DeviceUtils {

    /**
     * 네이게이션바 높이
     */
    fun getNavigationBarHeight(context: Context?): Int {
        return context?.let {
            val resourceIdBottom: Int =
                it.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return if (resourceIdBottom > 0) {
                it.resources.getDimensionPixelSize(resourceIdBottom)
            } else {
                0
            }
        } ?: run {
            0
        }
    }

}