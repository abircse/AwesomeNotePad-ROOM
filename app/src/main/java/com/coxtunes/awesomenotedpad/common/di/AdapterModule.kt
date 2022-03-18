package com.coxtunes.awesomenotedpad.common.di

import com.coxtunes.awesomenotedpad.ui.adapter.NotepadAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Provides
    fun provideNotepadAdapter(): NotepadAdapter = NotepadAdapter()

}