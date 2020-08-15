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

    val title: LiveData<String?>
    val countdown: LiveData<Int>
    val isSound: LiveData<Boolean>

    fun getData()

    fun submit(
        title: String? = null,
        countdown: Int = RecordConst.DEFAULT_COUNTDOWN,
        isSound: Boolean = true
    )
}

class SettingDataSourceImpl @Inject constructor(
    override val dispatcher: DispatcherProvider,
    private val preferencesManager: PreferencesManager
) : BaseDataSourceImpl(), SettingDataSource {

    private val _title = MutableLiveData<String?>()
    override val title: LiveData<String?> get() = _title

    private val _countdown = MutableLiveData<Int>()
    override val countdown: LiveData<Int> get() = _countdown

    private val _isSound = MutableLiveData<Boolean>()
    override val isSound: LiveData<Boolean> get() = _isSound

    override fun getData() {
        preferencesManager.run {
            _title.value = getString(RecordConst.PREF_RECORD_TITLE)
            _countdown.value =
                getInt(
                    RecordConst.PREF_RECORD_COUNTDOWN,
                    RecordConst.DEFAULT_COUNTDOWN
                )
            _isSound.value = getBoolean(RecordConst.PREF_RECORD_IS_SOUND, true)
        }
    }

    override fun submit(title: String?, countdown: Int, isSound: Boolean) {
        preferencesManager.run {
            put(RecordConst.PREF_RECORD_TITLE, title)
            put(RecordConst.PREF_RECORD_COUNTDOWN, countdown)
            put(RecordConst.PREF_RECORD_IS_SOUND, isSound)
        }
    }
}
