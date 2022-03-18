package com.coxtunes.awesomenotedpad.data.datasource.localsource

import com.coxtunes.awesomenotedpad.common.responsesealed.Resource
import com.coxtunes.awesomenotedpad.common.dbconfig.NotePadDao
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotePadDataSource @Inject constructor(
    private val notepaddao: NotePadDao
) {
    fun addNote(notepad: NotePad): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                delay(100)
                notepaddao.addNote(notepad)
                emit(Resource.Success("Added as Note"))

            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Server Error"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun updateNote(notepad: NotePad): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                delay(100)
                notepaddao.updateNote(notepad)
                emit(Resource.Success("Note Updated"))

            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Server Error"))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getNotes(): Flow<Resource<List<NotePad>>> {
        return notepaddao.getNotes().map {
            Resource.Success(it)
        }
    }

    fun deleteNote(notepad: NotePad): Flow<Resource<String>> {
        return flow {
            try {
                emit(Resource.Loading())
                delay(100)
                notepaddao.deleteNote(notepad)
                emit(Resource.Success("Note Item Deleted"))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Server Error"))
            }
        }.flowOn(Dispatchers.IO)
    }
}

