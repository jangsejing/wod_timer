package com.jess.wodtimer.presentation.setting

import androidx.hilt.lifecycle.ViewModelInject
import com.jess.wodtimer.common.base.BaseViewModel
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.domain.datasource.SettingDataSource

class SettingViewModel @ViewModelInject constructor(
    private val dataSource: SettingDataSource
) : BaseViewModel() {

    val ratio = dataSource.ratio
    val timerType = dataSource.timerType
    val timerMinute = dataSource.timerMinute
    val title = dataSource.title
    val countDown = dataSource.countDown
    val isSound = dataSource.isSound
    val isDate = dataSource.isDate

    fun getData() {
        dataSource.getData()
    }

    fun submit(
        ratio: RecordConst.Ratio,
        timerType: RecordConst.TimerType,
        timerMinute: String?,
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

        // 녹화시간
        val minute = if (!timerMinute.isNullOrEmpty()) {
            timerMinute.toInt()
        } else {
            RecordConst.MAX_RECORD_TIME
        }

        dataSource.submit(
            ratio,
            timerType,
            minute,
            title,
            time,
            isSound,
            isDate
        )
    }
}