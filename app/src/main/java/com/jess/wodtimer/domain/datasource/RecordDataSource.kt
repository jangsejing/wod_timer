package com.jess.wodtimer.domain.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.di.DispatcherProvider
import javax.inject.Inject

/**
 * @author jess
 * @since 2020.06.12
 */
interface RecordDataSource : BaseDataSource {
    val countDown: LiveData<Int>
}

class RecordDataSourceImpl @Inject constructor(
    override val dispatcher: DispatcherProvider
) : BaseDataSourceImpl(), RecordDataSource {

    private val _countDown = MutableLiveData<Int>()
    override val countDown: LiveData<Int> get() = _countDown

}
