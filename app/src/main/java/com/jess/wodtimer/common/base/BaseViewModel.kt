package com.jess.wodtimer.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 * @author jess
 * @since 2020.08.11
 */
abstract class BaseViewModel : ViewModel() {

    open var baseDataSource: BaseDataSource? = null
    open var isProgress: LiveData<Boolean>? = null

    override fun onCleared() {
        baseDataSource?.onCleared()
        super.onCleared()
    }
}