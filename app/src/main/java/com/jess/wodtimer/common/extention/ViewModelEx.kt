package com.jess.wodtimer.common.extention

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass


/**
 * @author jess
 * @since 2020.06.12
 */
fun <VM : ViewModel> ComponentActivity.createViewModel(
    classType: KClass<VM>
): VM = ViewModelProvider(this, defaultViewModelProviderFactory)[classType.java]
