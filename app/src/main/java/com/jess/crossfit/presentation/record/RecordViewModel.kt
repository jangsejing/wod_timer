package com.jess.crossfit.presentation.record

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jess.crossfit.common.base.BaseViewModel

class RecordViewModel @ViewModelInject constructor(

) : BaseViewModel() {


    fun test(): LiveData<String> {
        val test = MutableLiveData<String>()
        test.value = "hi"
        return test
    }

}