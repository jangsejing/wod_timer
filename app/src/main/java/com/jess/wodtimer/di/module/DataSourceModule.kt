package com.jess.wodtimer.di.module

import com.jess.wodtimer.domain.datasource.MediaDataSource
import com.jess.wodtimer.domain.datasource.MediaDataSourceImpl
import com.jess.wodtimer.domain.datasource.RecordDataSource
import com.jess.wodtimer.domain.datasource.RecordDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author jess
 * @since 2020.06.13
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindRecordDataSource(dataSource: RecordDataSourceImpl): RecordDataSource

    @Binds
    abstract fun bindMediaDataSource(dataSource: MediaDataSourceImpl): MediaDataSource

}