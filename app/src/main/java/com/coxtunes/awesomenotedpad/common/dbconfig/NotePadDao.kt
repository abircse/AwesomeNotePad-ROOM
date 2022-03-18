package com.coxtunes.awesomenotedpad.common.dbconfig

import androidx.room.*
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import kotlinx.coroutines.flow.Flow

@Dao
interface NotePadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(notepad: NotePad)

    @Delete
    suspend fun deleteNote(notepad: NotePad)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(notepad: NotePad)

    @Query("SELECT * FROM notepad")
    fun getNotes(): Flow<List<NotePad>>
}