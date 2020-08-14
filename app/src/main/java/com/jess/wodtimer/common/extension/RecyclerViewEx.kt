package com.jess.wodtimer.common.extension

import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jess.wodtimer.common.base.BaseRecyclerViewAdapter
import com.jess.wodtimer.common.util.tryCatch

/**
 * RecyclerView Adapter
 *
 * @param items
 * @param isClear
 */
@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["addItems", "isClear"], requireAll = false)
fun RecyclerView.addItems(
    items: List<Any>?,
    isClear: Boolean = true
) {
    tryCatch {
        (this.adapter as? BaseRecyclerViewAdapter<Any, ViewDataBinding>)?.run {
            if (isClear) {
                this.clear()
            }

            if (!items.isNullOrEmpty()) {
                this.addItems(items)
            }
        }
    }
}