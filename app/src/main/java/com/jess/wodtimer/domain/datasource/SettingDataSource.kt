package com.jess.wodtimer.domain.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.common.constant.RecordConst
import com.jess.wodtimer.common.manager.PreferencesManager
import com.jess.wodtimer.di.provider.DispatcherProvider
import javax.inject.Inject

/**
 * @author jess
 * @since 2020.08.11
 */
interface SettingDataSource : BaseDataSource {

    val ratio: LiveData<RecordConst.RATIO>
    val title: LiveData<String?>
    val countDown: LiveData<Int>
    val isSound: LiveData<Boolean>
    val isDate: LiveData<Boolean>

    fun getData()

    fun submit(
        ratio: RecordConst.RATIO,
        title: String? = null,
        countDown: Int = RecordConst.DEFAULT_COUNTDOWN,
        isSound: Boolean = true,
        isDate: Boolean = true
    )
}

class SettingDataSourceImpl @Inject constructor(
    override val dispatcher: DispatcherProvider,
    private val preferencesManager: PreferencesManager
) : BaseDataSourceImpl(), SettingDataSource {

    private val _ratio = MutableLiveData<RecordConst.RATIO>()
    override val ratio: LiveData<RecordConst.RATIO> get() = _ratio

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
            _ratio.value = RecordConst.RATIO.values()[getInt(
                RecordConst.PREF_RECORD_RATIO,
                RecordConst.RATIO.GENERAL.ordinal
            )]
            _title.value = getString(RecordConst.PREF_RECORD_TITLE)
            _countDown.value =
                getInt(
                    RecordConst.PREF_RECORD_COUNTDOWN,
                    RecordConst.DEFAULT_COUNTDOWN
                )
            _isSound.value = getBoolean(RecordConst.PREF_RECORD_IS_SOUND, true)
            _isDate.value = getBoolean(RecordConst.PREF_RECORD_IS_DATE, true)
        }
    }

    override fun submit(
        ratio: RecordConst.RATIO,
        title: String?,
        countDown: Int,
        isSound: Boolean,
        isDate: Boolean
    ) {
        preferencesManager.run {
            put(RecordConst.PREF_RECORD_RATIO, ratio.ordinal)
            put(RecordConst.PREF_RECORD_TITLE, title)
            put(RecordConst.PREF_RECORD_COUNTDOWN, countDown)
            put(RecordConst.PREF_RECORD_IS_SOUND, isSound)
            put(RecordConst.PREF_RECORD_IS_DATE, isDate)
        }
    }
}
