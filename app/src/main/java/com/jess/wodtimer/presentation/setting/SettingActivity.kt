package com.jess.wodtimer.presentation.setting

import android.app.Activity
import android.os.Bundle
import android.view.View
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
        initObserve()
    }

    private fun initObserve() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_finish -> {
                finish()
            }

            R.id.bt_submit -> {
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
