package com.jess.wodtimer.presentation.record.view

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jess.wodtimer.R
import com.jess.wodtimer.common.extension.createViewModel
import com.jess.wodtimer.common.util.DeviceUtils
import com.jess.wodtimer.databinding.RecordSettingBinding
import com.jess.wodtimer.presentation.record.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.record_setting.*
import timber.log.Timber
import java.util.*

class SettingBottomDialog(
    private val activity: ComponentActivity
) : BottomSheetDialog(activity), View.OnClickListener {

    private val binding: RecordSettingBinding = DataBindingUtil.inflate(
        layoutInflater,
        R.layout.record_setting,
        null,
        false
    )

    private val vm: SettingViewModel by lazy {
        activity.createViewModel(SettingViewModel::class)
    }

    private var countdownTime = 10
    private var title: String? = null
    var onSubmitListener: ((String?) -> Unit)? = null

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        initLayout()
        initObserve()
    }

    fun show(title: String?, listener: ((String?) -> Unit)) {
        this.title = title
        this.onSubmitListener = listener
        show()
    }

    private fun initLayout() {

        window?.attributes = WindowManager.LayoutParams().apply {
            copyFrom(window?.attributes)
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }


//        // 카운트 다운 시간 설정
//        val items = arrayListOf<String>()
//        for (i in 5..15 step 5) {
//            items.add(getSeconds(i))
//        }
//        tv_countdown.run {
//            setAdapter(ArrayAdapter(activity, R.layout.record_countdown_item, items))
//            setText(getSeconds(countdownTime), false)
//        }

        et_title.run {
            setText(title, TextView.BufferType.EDITABLE)
            if (!title.isNullOrEmpty()) {
                val index = title?.length ?: 0
                setSelection(index)
            }
        }
        vm.showKeyboard()

        arrayOf(tv_submit).forEach {
            (it as View).setOnClickListener(this)
        }

    }

    private fun initObserve() {
        vm.isShowKeyboard.observe(activity, Observer {
            DeviceUtils.showKeyboard(activity, et_title)
        })
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
//            R.id.iv_close -> {
//
//            }

            R.id.tv_submit -> {
//                val count = tv_countdown.text.toString()
//                val seconds = count.substring(0, count.length - 2).toInt()
                Timber.d("title : $title")
                onSubmitListener?.invoke(et_title.text.toString())
            }
        }
        dismiss()
    }
}