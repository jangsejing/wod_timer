package com.jess.wodtimer.presentation.setting

import androidx.hilt.lifecycle.ViewModelInject
import com.jess.wodtimer.common.base.BaseViewModel
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.domain.datasource.SettingDataSource

class SettingViewModel @ViewModelInject constructor(
    private val dataSource: SettingDataSource
) : BaseViewModel() {

    val title = dataSource.title
    val countDown = dataSource.countDown
    val isSound = dataSource.isSound
    val isDate = dataSource.isDate
    val ratio = dataSource.ratio

    fun getData() {
        dataSource.getData()
    }

    fun submit(
        ratio: RecordConst.RATIO,
        title: String?,
        countDown: String?,
        isSound: Boolean,
        isDate: Boolean
    ) {
        val time = if (!countDown.isNullOrEmpty()) {
            countDown.toInt()
        } else {
            RecordConst.DEFAULT_COUNTDOWN
        }

        dataSource.submit(
            ratio,
            title,
            time,
            isSound,
            isDate
        )
    }
}