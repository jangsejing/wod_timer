package com.jess.wodtimer.di.module

import com.jess.wodtimer.di.DispatcherProvider
import com.jess.wodtimer.di.DispatcherProviderImpl
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
abstract class DispatcherModule {

    @Binds
    abstract fun bindDispatchers(dispatcher: DispatcherProviderImpl): DispatcherProvider

}

