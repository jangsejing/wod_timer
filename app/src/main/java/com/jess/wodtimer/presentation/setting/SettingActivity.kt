package com.jess.wodtimer.presentation.setting

import android.app.Activity
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseActivity
import com.jess.wodtimer.databinding.SettingActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.header_view.*
import kotlinx.android.synthetic.main.setting_activity.*

/**
 * @author jess
 * @since 2020.08.11
 */
@AndroidEntryPoint
class SettingActivity : BaseActivity<SettingActivityBinding, SettingViewModel>(),
    View.OnClickListener {

    override val layoutRes get() = R.layout.setting_activity
    override val viewModelClass get() = SettingViewModel::class

    override fun initLayout() {

        arrayOf(iv_finish, bt_submit).forEach {
            (it as View).setOnClickListener(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        vm.getData()
        initListener()
    }

    private fun initListener() {
        ti_countdown.editText?.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank()) {
                val time = text.toString().toInt()
                if (isCountDownTimeNotValid(time)) {
                    ti_countdown.error = getString(R.string.setting_element_countdown_valid)
                    ti_countdown.isErrorEnabled = true
                }
            } else {
                ti_countdown.isErrorEnabled = false
            }
        }
    }

    private fun isCountDownTimeNotValid(time: Int): Boolean {
        return time < 5 || time > 60
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_finish -> {
                finish()
            }

            R.id.bt_submit -> {
                val time = et_countdown.editableText.toString().toInt()
                if (isCountDownTimeNotValid(time)) {
                    return
                }

                vm.submit(
                    et_title.text.toString(),
                    et_countdown.text.toString(),
                    swc_sound.isChecked
                )
                setResult(Activity.RESULT_OK)
                finish()
            }

        }
    }
}
