package com.jess.wodtimer.common.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jess.wodtimer.BR
import com.jess.wodtimer.common.util.tryCatch

/**
 * @author jess
 * @since 2020.06.12
 */
open class BaseViewHolder<T : Any?>(
    val viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(viewDataBinding.root) {

    open fun onBind(item: T?) {
        tryCatch {
            viewDataBinding.run {
                setVariable(BR.item, item)
                viewDataBinding.executePendingBindings()
            }
        }
    }
}