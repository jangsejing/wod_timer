package com.jess.wodtimer.presentation.record.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jess.wodtimer.common.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingViewModel @ViewModelInject constructor(

) : BaseViewModel() {

    private val _isShowKeyboard = MutableLiveData<Boolean>()
    val isShowKeyboard: LiveData<Boolean> get() = _isShowKeyboard

    fun showKeyboard() {
        viewModelScope.launch {
            delay(500)
            _isShowKeyboard.postValue(true)
        }
    }
}