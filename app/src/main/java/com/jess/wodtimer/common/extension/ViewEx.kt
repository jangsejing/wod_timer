package com.jess.wodtimer.common.extension

import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.record_activity.*
import kotlin.math.roundToInt

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

/**
 * 마진
 */
fun View.setMargin(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0
) {
    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.setMargins(
        left,
        top,
        right,
        bottom
    )
    this.layoutParams = layoutParams
}

/**
 * ConstraintLayout에 속한 View의 dimensionRatio 설정
 */
fun View.dimensionRatio(ratio: String = "1:0") {
    val params = (this.layoutParams as ConstraintLayout.LayoutParams).apply {
        dimensionRatio = ratio
    }
    this.layoutParams = params
}