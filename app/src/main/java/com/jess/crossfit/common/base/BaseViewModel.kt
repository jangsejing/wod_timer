package com.jess.crossfit.common.base

import androidx.lifecycle.ViewModel

/**
 * @author jess
 * @since 2020.06.12
 */
abstract class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}