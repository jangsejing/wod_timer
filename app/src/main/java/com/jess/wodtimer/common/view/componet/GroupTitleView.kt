package com.jess.wodtimer.common.view.componet

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.jess.wodtimer.R
import com.jess.wodtimer.databinding.GroupTitleViewBinding
import kotlinx.android.synthetic.main.group_title_view.view.*

/**
 * 공통 헤더뷰
 *
 * @author jess
 */
class GroupTitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding = GroupTitleViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        intLayout(attrs, defStyleAttr)
    }

    var title: String? = null
        set(value) {
            field = value
            tv_title.run {
                text = value
                visibility = View.VISIBLE
            }
        }

    @SuppressLint("CustomViewStyleable", "Recycle", "CheckResult")
    private fun intLayout(
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        if (attrs != null) {
            val typedValue = context.obtainStyledAttributes(
                attrs,
                R.styleable.GroupTitleView,
                defStyleAttr,
                0
            )

            title = typedValue.getString(R.styleable.GroupTitleView_title)
        }
    }

}