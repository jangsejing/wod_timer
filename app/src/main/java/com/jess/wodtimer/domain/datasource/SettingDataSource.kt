package com.jess.wodtimer.domain.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.R
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.common.manager.PreferencesManager
import com.jess.wodtimer.di.provider.DispatcherProvider
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlin.concurrent.timer

/**
 * @author jess
 * @since 2020.08.11
 */
interface SettingDataSource : BaseDataSource {

    val ratio: LiveData<RecordConst.Ratio>
    val timerType: LiveData<RecordConst.TimerType>
    val timerTypeDisplay: LiveData<String>
    val timerMinute: LiveData<Int>
    val title: LiveData<String?>
    val countDown: LiveData<Int>
    val isSound: LiveData<Boolean>
    val isDate: LiveData<Boolean>

    fun getData()

    fun submit(
        ratio: RecordConst.Ratio = RecordConst.Ratio.GENERAL,
        timerType: RecordConst.TimerType = RecordConst.TimerType.FOR_TIME,
        timerMinute: Int = RecordConst.MIN_RECORD_TIME,
        title: String? = null,
        countDown: Int = RecordConst.DEFAULT_COUNTDOWN,
        isSound: Boolean = true,
        isDate: Boolean = true
    )
}

class SettingDataSourceImpl @Inject constructor(
    @ActivityContext private val context: Context,
    override val dispatcher: DispatcherProvider,
    private val preferencesManager: PreferencesManager
) : BaseDataSourceImpl(), SettingDataSource {

    private val _ratio = MutableLiveData<RecordConst.Ratio>()
    override val ratio: LiveData<RecordConst.Ratio> get() = _ratio

    private val _timerType = MutableLiveData<RecordConst.TimerType>()
    override val timerType: LiveData<RecordConst.TimerType> get() = _timerType

    private val _timerTypeDisplay = MutableLiveData<String>()
    override val timerTypeDisplay: LiveData<String> get() = _timerTypeDisplay

    private val _timerMinute = MutableLiveData<Int>()
    override val timerMinute: LiveData<Int> get() = _timerMinute

    private val _title = MutableLiveData<String?>()
    override val title: LiveData<String?> get() = _title

    private val _countDown = MutableLiveData<Int>()
    override val countDown: LiveData<Int> get() = _countDown

    private val _isSound = MutableLiveData<Boolean>()
    override val isSound: LiveData<Boolean> get() = _isSound

    private val _isDate = MutableLiveData<Boolean>()
    override val isDate: LiveData<Boolean> get() = _isDate

    override fun getData() {
        preferencesManager.run {
            // 비율
            _ratio.value = RecordConst.Ratio.values()[getInt(
                RecordConst.PREF_RECORD_RATIO,
                RecordConst.Ratio.GENERAL.ordinal
            )]

            // 타이머 타입
            val type = RecordConst.TimerType.values()[getInt(
                RecordConst.PREF_RECORD_TIMER_TYPE,
                RecordConst.TimerType.FOR_TIME.ordinal
            )]
            _timerType.value = type
            _timerTypeDisplay.value = getTimerTypeDisplay(type)
            _timerMinute.value = getInt(RecordConst.PREF_RECORD_TIMER_MINUTE)

            // 제목
            _title.value = getString(RecordConst.PREF_RECORD_TITLE)

            // 카운트 다운
            _countDown.value =
                getInt(
                    RecordConst.PREF_RECORD_COUNTDOWN,
                    RecordConst.DEFAULT_COUNTDOWN
                )

            // 녹음
            _isSound.value = getBoolean(RecordConst.PREF_RECORD_IS_SOUND, true)

            // 날짜 표시
            _isDate.value = getBoolean(RecordConst.PREF_RECORD_IS_DATE, true)
        }
    }

    override fun submit(
        ratio: RecordConst.Ratio,
        timerType: RecordConst.TimerType,
        timerMinute: Int,
        title: String?,
        countDown: Int,
        isSound: Boolean,
        isDate: Boolean
    ) {

//        val minute = if (timerType == RecordConst.TimerType.FOR_TIME) {
//            RecordConst.MAX_RECORD_TIME
//        } else {
//            timerMinute
//        }

        preferencesManager.run {
            put(RecordConst.PREF_RECORD_RATIO, ratio.ordinal)
            put(RecordConst.PREF_RECORD_TIMER_TYPE, timerType.ordinal)
            put(RecordConst.PREF_RECORD_TIMER_MINUTE, timerMinute)
            put(RecordConst.PREF_RECORD_TITLE, title)
            put(RecordConst.PREF_RECORD_COUNTDOWN, countDown)
            put(RecordConst.PREF_RECORD_IS_SOUND, isSound)
            put(RecordConst.PREF_RECORD_IS_DATE, isDate)
        }
    }

    /**
     * 타이머 타입 디스플레이
     */
    private fun getTimerTypeDisplay(type: RecordConst.TimerType): String {
        return when (type) {
            RecordConst.TimerType.TIME_CAP -> context.getString(R.string.setting_element_timer_type_time_cap)
            RecordConst.TimerType.AMRAP -> context.getString(R.string.setting_element_timer_type_amrap)
            else -> context.getString(R.string.setting_element_timer_type_for_time)
        }
    }
}
