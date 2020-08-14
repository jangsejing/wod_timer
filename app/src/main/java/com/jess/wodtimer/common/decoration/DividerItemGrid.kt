package com.jess.wodtimer.common.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Grid LayoutManager 간격 조절
 * @property space
 */
class DividerItemGrid(
    private val space: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val manager = parent.layoutManager as GridLayoutManager
        val spanCount = manager.spanCount

        val maxCount = parent.adapter?.itemCount ?: 0
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        val row = position / spanCount
        val lastRow = (maxCount - 1) / spanCount

        outRect.left = column * space / spanCount
        outRect.right = space - (column + 1) * space / spanCount

        if (row > 0) {
            outRect.top = space
        }

        if (row == lastRow) {
            outRect.bottom = space
        }
    }

}