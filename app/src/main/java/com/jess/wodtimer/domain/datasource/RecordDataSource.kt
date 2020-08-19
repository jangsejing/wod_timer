package com.jess.wodtimer.domain.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.common.util.DateUtils
import com.jess.wodtimer.di.provider.DispatcherProvider
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.DateFormat
import java.util.*
import java.util.logging.Logger
import javax.inject.Inject

/**
 * @author jess
 * @since 2020.08.11
 */
interface RecordDataSource : BaseDataSource {

    val settingDataSource: SettingDataSource

    val date: LiveData<String>
    val countDown: LiveData<Int>
    val time: LiveData<Long>
    val isPlay: LiveData<Boolean>
    val isCountDown: LiveData<Boolean>

    val isBeepShort: LiveData<Boolean>
    val isBeepLong: LiveData<Boolean>

    var countDownTime: Int // 카운트 타임 시간

    fun getDate()
    fun setData()
    fun reset()
    fun onStart()
    fun onCountDown()
    fun forTime()
    fun timeCapAndAmrap(minute: Int)
    fun onStop()

}

class RecordDataSourceImpl @Inject constructor(
    @ActivityContext val context: Context,
    override val dispatcher: DispatcherProvider,
    override val settingDataSource: SettingDataSource
) : BaseDataSourceImpl(), RecordDataSource {

    override var countDownTime = RecordConst.DEFAULT_COUNTDOWN // 카운트 다운 시간

    private val _date = MutableLiveData<String>()
    override val date: LiveData<String> get() = _date

    private val _countDown = MutableLiveData<Int>()
    override val countDown: LiveData<Int> get() = _countDown

    private val _time = MutableLiveData<Long>()
    override val time: LiveData<Long> get() = _time

    private val _isPlay = MutableLiveData<Boolean>()
    override val isPlay: LiveData<Boolean> get() = _isPlay

    private val _isCountDown = MutableLiveData<Boolean>()
    override val isCountDown: LiveData<Boolean> get() = _isCountDown

    private val _isBeepShort = MutableLiveData<Boolean>()
    override val isBeepShort: LiveData<Boolean> get() = _isBeepShort

    private val _isBeepLong = MutableLiveData<Boolean>()
    override val isBeepLong: LiveData<Boolean> get() = _isBeepLong

    /**
     * 날짜
     */
    override fun getDate() {
        _date.value = DateUtils.getFormat("yy.MM.dd (E)")
    }

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
        _time.postValue(0) // 시간 초기화
        _isPlay.postValue(false) // 플레이 구분 값 초기화
        _countDown.postValue(0) // 카운트 다운 초기화
        _isCountDown.postValue(false) // 카운트다운 구분 값 초기화
    }

    override fun onStart() {
        reset()
        onCountDown()
    }

    /**
     * 카운트 다운
     */
    override fun onCountDown() {
        _isCountDown.value = true

        CoroutineScope(dispatcher.default).launch {
            repeat(countDownTime + 1) {

                val countDown = countDownTime - it
                _countDown.postValue(countDown)
                if (countDown in 1..3) {
                    _isBeepShort.postValue(true)
                }
                Timber.d("countDown $it")

                if (countDownTime == it) {
                    _isPlay.postValue(true)
                    _isBeepLong.postValue(true)
                    Timber.d("countDown Finished $it")

                    val minute = (settingDataSource.timerMinute.value ?: 10) * 60 // 10분
                    when (settingDataSource.timerType.value) {
                        RecordConst.TimerType.TIME_CAP,
                        RecordConst.TimerType.AMRAP -> timeCapAndAmrap(minute)
                        else -> forTime()
                    }
                } else {
                    delay(1000)
                }
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

    /**
     * For Time
     */
    override fun forTime() {
        CoroutineScope(dispatcher.default).launch {
            repeat(RecordConst.MAX_RECORD_TIME) {
                _time.postValue((it * 1000).toLong())
                delay(1000)
            }
        }
    }

    /**
     * Time Cap
     * 정해진 시간 안에 빨리 하기
     *
     * AMRAP
     * 정해진 시간 안에 많은 라운드
     *
     */
    override fun timeCapAndAmrap(minute: Int) {
        CoroutineScope(dispatcher.default).launch {

            repeat(minute + 1) {
                val countDown = minute - it

                if (countDown in 1..3) {
                    _isBeepShort.postValue(true)
                }
                _time.postValue((countDown * 1000).toLong())

                if (minute == it) {
                    // 종료
                    _isBeepLong.postValue(true)
                } else {
                    delay(1000)
                }
            }

            // 끝나고 3초의 여유를 준다
            delay(3000)
            onStop()

        }
    }
}
