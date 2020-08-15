package com.jess.wodtimer.presentation.setting

import androidx.hilt.lifecycle.ViewModelInject
import com.jess.wodtimer.common.base.BaseViewModel
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.domain.datasource.SettingDataSource

class SettingViewModel @ViewModelInject constructor(
    private val dataSource: SettingDataSource
) : BaseViewModel() {

    val title = dataSource.title
    val countdown = dataSource.countdown
    val isSound = dataSource.isSound

    fun getData() {
        dataSource.getData()
    }

    fun submit(title: String?, countdown: String?, isSound: Boolean) {
        dataSource.submit(
            title,
            countdown?.toInt() ?: RecordConst.DEFAULT_COUNTDOWN,
            isSound
        )
    }
}