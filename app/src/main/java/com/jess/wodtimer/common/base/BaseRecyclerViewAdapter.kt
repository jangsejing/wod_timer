package com.jess.wodtimer.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jess.wodtimer.common.extension.setCircleRipple
import com.jess.wodtimer.common.extension.setRipple

/**
 * @author jess
 * @since 2020.06.12
 */
abstract class BaseRecyclerViewAdapter<ITEM : Any, VD : ViewDataBinding>(
    @LayoutRes private val layoutId: Int = 0
) : RecyclerView.Adapter<BaseViewHolder<ITEM>>() {

    private val list = mutableListOf<ITEM>()
    private var itemClickListener: ((View, ITEM?) -> Unit)? = null
    private var isCircleRipple: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM> {

        require(layoutId > 0) { "Empty Layout Resource" }

        val dataBinding = createViewDataBinding(parent, layoutId)
        val viewHolder = createViewHolder(dataBinding)

        dataBinding.run {

            // onClick
            itemClickListener?.let { listener ->
                root.run {
                    if (isCircleRipple) setCircleRipple() else setRipple()
                    setOnClickListener { view ->
                        if (viewHolder.adapterPosition != RecyclerView.NO_POSITION) {
                            listener.invoke(view, list[viewHolder.adapterPosition])
                        }
                    }
                }
            }
        }
        return viewHolder
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<ITEM>, position: Int) {
        holder.onBind(list[position])
        onBindData(position, list[position], holder.viewDataBinding as VD)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    fun addItems(items: List<ITEM>) = apply {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: ITEM) = apply {
        list.add(item)
        notifyDataSetChanged()
    }

    fun clear() = apply {
        list.clear()
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItems(): List<ITEM> {
        return list
    }

    /**
     * 아이템 클릭 리스너
     *
     * @param listener
     * @param isCircleRipple
     */
    open fun setOnItemClickListener(
        isCircleRipple: Boolean = false,
        listener: ((View, ITEM?) -> Unit)
    ) {
        this.itemClickListener = listener
        this.isCircleRipple = isCircleRipple
    }

    open fun createViewHolder(
        dataBinding: ViewDataBinding
    ): BaseViewHolder<ITEM> {
        return BaseViewHolder(dataBinding)
    }

    open fun createViewDataBinding(parent: ViewGroup, layoutId: Int): ViewDataBinding {
        return DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
    }

    open fun onBindData(position: Int, data: ITEM?, dataBinding: VD) {

    }

}