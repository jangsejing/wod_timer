package com.jess.wodtimer.common.extension

import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat

/**
 * @author jess
 * @since 2020.08.11
 */

/**
 * Ripple 사각형
 */
fun View.setRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground = ContextCompat.getDrawable(context, resourceId)
    } else {
        setBackgroundResource(resourceId)
    }
}

/**
 * Ripple 원형
 */
fun View.setCircleRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, this, true)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        foreground = ContextCompat.getDrawable(context, resourceId)
    } else {
        setBackgroundResource(resourceId)
    }
}