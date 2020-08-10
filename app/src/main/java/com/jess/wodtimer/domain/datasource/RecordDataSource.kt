package com.jess.wodtimer.domain.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.di.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author jess
 * @since 2020.06.12
 */
interface RecordDataSource : BaseDataSource {

    val countDown: LiveData<Int>
    val isFinishedCountDown: LiveData<Boolean>
    var countDownValue: Int

    fun onRecord()
}

class RecordDataSourceImpl @Inject constructor(
    override val dispatcher: DispatcherProvider
) : BaseDataSourceImpl(), RecordDataSource {

    override var countDownValue: Int = 5

    private val _countDown = MutableLiveData<Int>()
    override val countDown: LiveData<Int> get() = _countDown

    private val _isFinishedCountDown = MutableLiveData<Boolean>()
    override val isFinishedCountDown: LiveData<Boolean> get() = _isFinishedCountDown

    /**
     * 녹화 시작
     */
    override fun onRecord() {
        CoroutineScope(dispatcher.default()).launch {
            repeat(countDownValue + 1) {
                delay(1000)
                _countDown.postValue(countDownValue - it)
                Timber.d("countDown $it")

                if (countDownValue == it) {
                    Timber.d("countDown Finished $it")
                    _isFinishedCountDown.postValue(true)
                }
            }
        }
    }
}
