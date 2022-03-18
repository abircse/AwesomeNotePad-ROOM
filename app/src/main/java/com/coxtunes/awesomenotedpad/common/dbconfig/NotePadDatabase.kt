package com.coxtunes.awesomenotedpad.common.dbconfig

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coxtunes.awesomenotedpad.data.dto.NotePad

@Database(
    entities = [
        NotePad::class
    ],
    version = 2,
    exportSchema = true
)

abstract class NotePadDatabase : RoomDatabase() {
    abstract fun getNotePadDao(): NotePadDao
}