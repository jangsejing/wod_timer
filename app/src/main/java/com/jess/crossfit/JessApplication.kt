package com.jess.crossfit

import android.app.Application
import timber.log.Timber

/**
 * @author jess
 * @since 2020.06.12
 */
class JessApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
