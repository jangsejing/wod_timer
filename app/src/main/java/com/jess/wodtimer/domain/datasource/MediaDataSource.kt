package com.jess.wodtimer.domain.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.manager.MediaManager
import com.jess.wodtimer.data.MediaData
import com.jess.wodtimer.di.provider.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * @author jess
 * @since 2020.08.11
 */
interface MediaDataSource : BaseDataSource {

    val mediaList: LiveData<List<MediaData>>

    fun getVideoList(context: Context?)

}

class MediaDataSourceImpl @Inject constructor(
    override val dispatcher: DispatcherProvider
) : MediaDataSource {

    private val _mediaList = MutableLiveData<List<MediaData>>()
    override val mediaList: LiveData<List<MediaData>> get() = _mediaList

    /**
     * 동영상 리스트
     */
    override fun getVideoList(context: Context?) {
        CoroutineScope(dispatcher.main).launch {
            _mediaList.value = MediaManager.getVideoList(context)
        }
    }
}
