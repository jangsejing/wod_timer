package com.jess.wodtimer.common.bindingadapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jess.wodtimer.common.extension.addItems

/**
 * @author jess
 * @since 2020.08.12
 */
object RecyclerViewAdapter {

    /**
     * RecyclerView Adapter
     *
     * @param items
     * @param isClear
     */
    @JvmStatic
    @BindingAdapter(value = ["items", "isClear"], requireAll = false)
    fun addItems(
        view: RecyclerView,
        items: List<Any>?,
        isClear: Boolean = true
    ) {
        view.addItems(items, isClear)
    }
}