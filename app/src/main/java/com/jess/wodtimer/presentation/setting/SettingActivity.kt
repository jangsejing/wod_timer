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

    private var ratio: RecordConst.RATIO = RecordConst.RATIO.GENERAL

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
        vm.ratio.observe(this, Observer {
            when (it) {
                RecordConst.RATIO.INSTAGRAM -> rb_ratio_instagram.isChecked = true
                else -> rb_ratio_general.isChecked = true
            }
        })
    }

    private fun initListener() {
        rg_ratio.setOnCheckedChangeListener { radioGroup, i ->
            ratio = if (i == R.id.rb_ratio_instagram) {
                RecordConst.RATIO.INSTAGRAM
            } else {
                RecordConst.RATIO.GENERAL
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
