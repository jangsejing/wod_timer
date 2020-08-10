package com.jess.wodtimer.common.base

import androidx.lifecycle.ViewModel

/**
 * @author jess
 * @since 2020.06.12
 */
abstract class BaseViewModel : ViewModel() {

    open var baseDataSource: BaseDataSource? = null

    override fun onCleared() {
        baseDataSource?.onCleared()
        super.onCleared()
    }
}