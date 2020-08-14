package com.jess.wodtimer.common.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * @author jess
 * @since 2020.08.14
 */

fun ImageView.loadImage(any: Any?) {
    Glide.with(this)
        .load(any)
        .transition(DrawableTransitionOptions.withCrossFade())
        .centerCrop()
        .into(this)
}