package com.jess.wodtimer.presentation.record.viewmodel

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

    val title = dataSource.settingDataSource.title
    val isSound = dataSource.settingDataSource.isSound

    val time = dataSource.time
    val isPlay = dataSource.isPlay
    val isCountDown = dataSource.isCountDown

    init {
        dataSource.reset()
    }

    val countDown = dataSource.countDown

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

    fun setData() {
        dataSource.setData()
    }
}