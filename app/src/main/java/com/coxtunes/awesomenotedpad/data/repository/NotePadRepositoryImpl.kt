package com.coxtunes.awesomenotedpad.data.repository

import com.coxtunes.awesomenotedpad.common.responsesealed.Resource
import com.coxtunes.awesomenotedpad.data.datasource.localsource.NotePadDataSource
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import com.coxtunes.awesomenotedpad.domain.repository.NotePadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotePadRepositoryImpl @Inject constructor(val dataSource: NotePadDataSource) :
    NotePadRepository {

    override fun addNote(notepad: NotePad): Flow<Resource<String>> {
        return dataSource.addNote(notepad)
    }

    override fun updateNote(notepad: NotePad): Flow<Resource<String>> {
        return dataSource.updateNote(notepad)
    }

    override fun getNotes(): Flow<Resource<List<NotePad>>> {
        return dataSource.getNotes()
    }

    override fun deleteNote(notepad: NotePad): Flow<Resource<String>> {
        return dataSource.deleteNote(notepad)
    }

}