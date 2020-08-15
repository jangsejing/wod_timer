package com.jess.wodtimer.di.module

import com.jess.wodtimer.common.base.BaseDataSource
import com.jess.wodtimer.common.base.BaseDataSourceImpl
import com.jess.wodtimer.domain.datasource.*
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
    abstract fun bindBaseDataSource(dataSource: BaseDataSourceImpl): BaseDataSourceImpl

    @Binds
    abstract fun bindRecordDataSource(dataSource: RecordDataSourceImpl): RecordDataSource

    @Binds
    abstract fun bindMediaDataSource(dataSource: MediaDataSourceImpl): MediaDataSource

    @Binds
    abstract fun bindSettingDataSource(dataSource: SettingDataSourceImpl): SettingDataSource

}