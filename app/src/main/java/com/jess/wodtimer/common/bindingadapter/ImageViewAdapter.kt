package com.jess.wodtimer.common.bindingadapter

import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jess.wodtimer.common.extension.loadImage

/**
 * @author jess
 * @since 2020.08.12
 */
object ImageViewAdapter {

    @JvmStatic
    @BindingAdapter("loadUri")
    fun loadUri(view: ImageView, uri: Uri?) {
        view.loadImage(uri)
    }

    @JvmStatic
    @BindingAdapter("loadBitmap")
    fun loadBitmap(view: ImageView, bitmap: Bitmap?) {
        view.loadImage(bitmap)
    }
}