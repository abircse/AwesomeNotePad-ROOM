package com.coxtunes.awesomenotedpad.common.di

import android.content.Context
import androidx.room.Room
import com.coxtunes.awesomenotedpad.common.dbconfig.NotePadDao
import com.coxtunes.awesomenotedpad.common.dbconfig.NotePadDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideIptvLocalDatabase(@ApplicationContext applicationContext: Context): NotePadDatabase {
        return Room.databaseBuilder(
            applicationContext,
            NotePadDatabase::class.java,
            "notepad_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNotePadDao(notepad: NotePadDatabase): NotePadDao {
        return notepad.getNotePadDao()
    }
}