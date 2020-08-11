package com.jess.wodtimer.domain.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.di.provider.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author jess
 * @since 2020.08.11
 */
interface RecordDataSource : BaseDataSource {

    val countDown: LiveData<Int>
    val time: LiveData<Long>
    val isPlay: LiveData<Boolean>
    var countDownTime: Int // 카운트 타임 시간
    var maxRepeatTime: Int // 맥스 시간

    fun onStart()
    fun onCountDown()
    fun onPlay()
    fun onStop()

}

class RecordDataSourceImpl @Inject constructor(
    override val dispatcher: DispatcherProvider
) : BaseDataSourceImpl(), RecordDataSource {

    override var countDownTime: Int = 3
    override var maxRepeatTime: Int = 3600 // 1시간

    private val _countDown = MutableLiveData<Int>()
    override val countDown: LiveData<Int> get() = _countDown

    private val _time = MutableLiveData<Long>()
    override val time: LiveData<Long> get() = _time

    private val _isPlay = MutableLiveData<Boolean>()
    override val isPlay: LiveData<Boolean> get() = _isPlay

    override fun onStart() {
        _isPlay.value = false
        onCountDown()
    }

    /**
     * 녹화 시작
     */
    override fun onCountDown() {
        CoroutineScope(dispatcher.default()).launch {
            repeat(countDownTime + 1) {
                _countDown.postValue(countDownTime - it)
                Timber.d("countDown $it")

                if (countDownTime == it) {
                    Timber.d("countDown Finished $it")
                    onPlay()
                } else {
                    delay(1000)
                }
            }
        }
    }

    /**
     * 타이머
     */
    override fun onPlay() {
        _isPlay.postValue(true)
        CoroutineScope(dispatcher.default()).launch {
            repeat(maxRepeatTime) {
                _time.postValue((it * 1000).toLong())
                delay(1000)
            }
        }
    }


    override fun onStop() {

    }


}
