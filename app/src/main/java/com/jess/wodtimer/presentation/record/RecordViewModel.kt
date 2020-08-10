package com.jess.wodtimer.presentation.record

import androidx.hilt.lifecycle.ViewModelInject
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseViewModel
import com.jess.wodtimer.domain.datasource.RecordDataSource

class RecordViewModel @ViewModelInject constructor(
    private val dataSource: RecordDataSource
) : BaseViewModel() {

    override var baseDataSource: BaseDataSource? = dataSource
    val countDown = dataSource.countDown

    fun onRecord() {

    }

}