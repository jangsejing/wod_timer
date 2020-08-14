package com.jess.wodtimer.di.module

import com.jess.wodtimer.di.provider.ContextProvider
import com.jess.wodtimer.di.provider.ContextProviderImpl
import com.jess.wodtimer.di.provider.DispatcherProvider
import com.jess.wodtimer.di.provider.DispatcherProviderImpl
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
abstract class ContextModule {

    @Binds
    abstract fun bindContext(contextProvider: ContextProviderImpl): ContextProvider

}

