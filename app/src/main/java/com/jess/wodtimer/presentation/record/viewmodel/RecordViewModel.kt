package com.jess.wodtimer.presentation.record.viewmodel

import android.net.Uri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseViewModel
import com.jess.wodtimer.domain.datasource.RecordDataSource

class RecordViewModel @ViewModelInject constructor(
    private val dataSource: RecordDataSource
) : BaseViewModel() {

    override var baseDataSource: BaseDataSource? = dataSource

    val time = dataSource.time
    val isPlay = dataSource.isPlay
    val isCountDown = dataSource.isCountDown

    var recentMedialUri: Uri? = null

    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?> get() = _title

    init {
        dataSource.reset()
    }

    // 카운트 다운 데이터
    var countDownTime: Int = 0
        set(value) {
            field = value
            dataSource.countDownTime = field
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

    /**
     * 제목
     */
    fun setTitle(title: String?) {
        _title.value = title
    }
}