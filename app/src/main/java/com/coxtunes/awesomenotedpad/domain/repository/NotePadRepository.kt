package com.coxtunes.awesomenotedpad.domain.repository

import com.coxtunes.awesomenotedpad.common.responsesealed.Resource
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import kotlinx.coroutines.flow.Flow

interface NotePadRepository {
    fun addNote(notepad: NotePad): Flow<Resource<String>>
    fun updateNote(notepad: NotePad): Flow<Resource<String>>
    fun getNotes(): Flow<Resource<List<NotePad>>>
    fun deleteNote(notepad: NotePad): Flow<Resource<String>>
}