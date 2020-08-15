package com.jess.wodtimer.di.module

import com.jess.wodtimer.common.manager.PreferencesManager
import com.jess.wodtimer.common.manager.PreferencesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * @author jess
 * @since 2020.06.15
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindPreferencesManager(preferencesManagerImpl: PreferencesManagerImpl): PreferencesManager

}

