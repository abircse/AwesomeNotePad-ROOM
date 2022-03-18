package com.coxtunes.awesomenotedpad.common.di

import com.coxtunes.awesomenotedpad.data.datasource.localsource.NotePadDataSource
import com.coxtunes.awesomenotedpad.data.repository.NotePadRepositoryImpl
import com.coxtunes.awesomenotedpad.domain.repository.NotePadRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideNotePadRepository(
        dataSource: NotePadDataSource
    ): NotePadRepository = NotePadRepositoryImpl(dataSource)

}