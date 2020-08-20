package com.jess.wodtimer.presentation.record

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseViewModel
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.common.manager.PreferencesManager
import com.jess.wodtimer.domain.datasource.RecordDataSource
import com.jess.wodtimer.domain.datasource.SettingDataSource

class RecordViewModel @ViewModelInject constructor(
    private val dataSource: RecordDataSource
) : BaseViewModel() {

    override var baseDataSource: BaseDataSource? = dataSource

    val time = dataSource.time
    val isPlay = dataSource.isPlay
    val isCountDown = dataSource.isCountDown
    val date = dataSource.date
    val countDown = dataSource.countDown

    // 비프음
    val isBeepShort = dataSource.isBeepShort
    val isBeepLong = dataSource.isBeepLong

    // 설정
    val facing = dataSource.settingDataSource.facing
    val ratio = dataSource.settingDataSource.ratio
    val timerType = dataSource.settingDataSource.timerType
    val timerTypeDisplay = dataSource.settingDataSource.timerTypeDisplay
    val title = dataSource.settingDataSource.title
    val isSound = dataSource.settingDataSource.isSound
    val isDate = dataSource.settingDataSource.isDate

    init {
        dataSource.run {
            getDate()
            reset()
        }
    }

    /**
     * 시작
     */
    fun onRecord() {
        dataSource.onCountDown()
    }

    /**
     * 중지
     */
    fun onStop() {
        dataSource.onStop()
    }

    /**
     * 데이터 세팅
     * 앱 진입, 환경 설정 후 진입
     */
    fun setData() {
        dataSource.setData()
    }
}