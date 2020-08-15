package com.jess.wodtimer.presentation.media

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseViewModel
import com.jess.wodtimer.domain.datasource.MediaDataSource

class VideoListViewModel @ViewModelInject constructor(
    private val dataSource: MediaDataSource
) : BaseViewModel() {

    override var baseDataSource: BaseDataSource? = dataSource
    override var isProgress: LiveData<Boolean>? = dataSource.isProgress

    val mediaList = dataSource.mediaList

    fun getVideoList(context: Context?) {
        context?.let {
            dataSource.getVideoList(context)
        }
    }
}