package com.coxtunes.awesomenotedpad.common.di

import com.coxtunes.awesomenotedpad.common.dbconfig.NotePadDao
import com.coxtunes.awesomenotedpad.data.datasource.localsource.NotePadDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideNotePadDataSource(
        dao: NotePadDao
    ): NotePadDataSource {
        return NotePadDataSource(dao)
    }

}