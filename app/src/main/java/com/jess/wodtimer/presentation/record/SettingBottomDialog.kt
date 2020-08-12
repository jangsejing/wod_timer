package com.jess.wodtimer.presentation.record

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jess.wodtimer.R
import com.jess.wodtimer.databinding.RecordSettingBinding
import kotlinx.android.synthetic.main.record_setting.*
import java.util.*

class SettingBottomDialog(
    private val activity: Activity
) : BottomSheetDialog(activity), View.OnClickListener {

    private val binding: RecordSettingBinding = DataBindingUtil.inflate(
        layoutInflater,
        R.layout.record_setting,
        null,
        false
    )

    private val countdownTime = 10

    init {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.attributes = WindowManager.LayoutParams().apply {
            copyFrom(window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.MATCH_PARENT
        }

        initLayout()
    }

    override fun show() {
        super.show()
    }

    private fun initLayout() {

        // 카운트 다운 시간 설정
        val items = arrayListOf<String>()
        for (i in 5..15 step 5) {
            items.add(getSeconds(i))
        }
        tv_countdown.run {
            setAdapter(ArrayAdapter(activity, R.layout.record_countdown_item, items))
            setText(getSeconds(countdownTime), false)
        }

        arrayOf(iv_close, bt_submit).forEach {
            (it as View).setOnClickListener(this)
        }
    }

    /**
     * 초 반환
     * 1s, 1초
     *
     * @param seconds
     * @return
     */
    private fun getSeconds(seconds: Int): String {
        return String.format(
            Locale.getDefault(), context.getString(R.string.seconds), seconds
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_close -> {

            }

            R.id.bt_submit -> {

            }
        }
        dismiss()
    }
}