package com.jess.wodtimer.presentation.setting

import android.app.Activity
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseActivity
import com.jess.wodtimer.common.constant.RecordConst
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

    private var ratio = RecordConst.Ratio.GENERAL
    private var timerType = RecordConst.TimerType.FOR_TIME

    override fun initLayout() {
        arrayOf(iv_finish, bt_submit).forEach {
            (it as View).setOnClickListener(this)
        }
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        vm.getData()
        initObserve()
        initListener()
    }

    private fun initObserve() {

    }

    private fun initListener() {
        rg_ratio.setOnCheckedChangeListener { radioGroup, i ->
            ratio = if (i == R.id.rb_ratio_instagram) {
                RecordConst.Ratio.INSTAGRAM
            } else {
                RecordConst.Ratio.GENERAL
            }
        }

        rg_timer_type.setOnCheckedChangeListener { radioGroup, i ->
            ti_timer_type_minute.visibility = View.VISIBLE
            timerType = when (i) {
                R.id.rb_ratio_time_cap -> {
                    RecordConst.TimerType.TIME_CAP
                }
                R.id.rb_ratio_amrap -> {
                    RecordConst.TimerType.AMRAP
                }
//                R.id.rb_ratio_emom -> {
//                    RecordConst.TimerType.EMOM
//                }
                else -> {
                    ti_timer_type_minute.visibility = View.GONE
                    RecordConst.TimerType.FOR_TIME
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_finish -> {
                finish()
            }

            R.id.bt_submit -> {
                vm.submit(
                    ratio,
                    timerType,
                    et_timer_type_minute.text.toString(),
                    et_title.text.toString(),
                    et_countdown.text.toString(),
                    swc_sound.isChecked,
                    swc_date.isChecked
                )
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}
