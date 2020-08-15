package com.jess.wodtimer.domain.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.common.manager.PreferencesManager
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

    val settingDataSource: SettingDataSource

    val countDown: LiveData<Int>
    val time: LiveData<Long>
    val isPlay: LiveData<Boolean>
    val isCountDown: LiveData<Boolean>
    val isCountDownBeep: LiveData<Boolean>
    var countDownTime: Int // 카운트 타임 시간
    var maxRepeatTime: Int // 맥스 시간

    fun setData()
    fun reset()
    fun onStart()
    fun onCountDown()
    fun onPlay()
    fun onStop()

}

class RecordDataSourceImpl @Inject constructor(
    override val dispatcher: DispatcherProvider,
    override val settingDataSource: SettingDataSource
) : BaseDataSourceImpl(), RecordDataSource {

    override var countDownTime: Int = RecordConst.DEFAULT_COUNTDOWN // 카운트 다운 시간
    override var maxRepeatTime: Int = RecordConst.MAX_RECORD_TIME // 최대 1시간

    private val _countDown = MutableLiveData<Int>()
    override val countDown: LiveData<Int> get() = _countDown

    private val _time = MutableLiveData<Long>()
    override val time: LiveData<Long> get() = _time

    private val _isPlay = MutableLiveData<Boolean>()
    override val isPlay: LiveData<Boolean> get() = _isPlay

    private val _isCountDown = MutableLiveData<Boolean>()
    override val isCountDown: LiveData<Boolean> get() = _isCountDown

    private val _isCountDownBeep = MutableLiveData<Boolean>()
    override val isCountDownBeep: LiveData<Boolean> get() = _isCountDownBeep

    override fun setData() {
        settingDataSource.run {
            getData()
            countDown.value?.let {
                countDownTime = it
            }
        }
    }

    override fun reset() {
        dispatcher.createJob()
        _time.value = 0 // 시간 초기화
        _isPlay.value = false // 플레이 구분 값 초기화
        _countDown.value = 0 // 카운트 다운 초기화
        _isCountDown.value = false // 카운트다운 구분 값 초기화
    }

    override fun onStart() {
        reset()
        onCountDown()
    }

    /**
     * 녹화 시작
     */
    override fun onCountDown() {
        _isCountDown.value = true
        CoroutineScope(dispatcher.default).launch {
            repeat(countDownTime + 1) {

                val countDown = countDownTime - it
                _countDown.postValue(countDown)
                if (countDown in 1..3) {
                    _isCountDownBeep.postValue(true)
                }
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
        CoroutineScope(dispatcher.default).launch {
            repeat(maxRepeatTime) {
                _time.postValue((it * 1000).toLong())
                delay(1000)
            }
        }
    }

    /**
     * 중지
     */
    override fun onStop() {

        // 현재 진행 상태
        val isCountDown = isCountDown.value ?: false
        val isPlay = isPlay.value ?: false

        // 데이터 초기화
        cancel()
        reset()
    }


}
