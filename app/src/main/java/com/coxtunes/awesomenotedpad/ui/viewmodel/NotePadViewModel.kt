package com.coxtunes.awesomenotedpad.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coxtunes.awesomenotedpad.common.responsesealed.Resource
import com.coxtunes.awesomenotedpad.data.dto.NotePad
import com.coxtunes.awesomenotedpad.domain.repository.NotePadRepository
import com.coxtunes.awesomenotedpad.ui.uistate.AddNotePadUIState
import com.coxtunes.awesomenotedpad.ui.uistate.NotePadUIState
import com.coxtunes.awesomenotedpad.ui.uistate.UpdateNotePadUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotePadViewModel @Inject constructor(private val repository: NotePadRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(NotePadUIState())
    val uiState = _uiState.asStateFlow()

    private val _adduiState = MutableStateFlow(AddNotePadUIState())
    val adduiState = _adduiState.asStateFlow()

    private val _updateuiState = MutableStateFlow(UpdateNotePadUIState())
    val updateuiState = _updateuiState.asStateFlow()

    private var notesJob: Job? = null
    fun addNote(notePad: NotePad) {
        notesJob?.cancel()
        notesJob = viewModelScope.launch {
            repository.addNote(notePad).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _adduiState.update {
                            it.copy(
                                isLoading = true,
                                message = ""
                            )
                        }
                    }
                    is Resource.Success -> {
                        _adduiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.data ?: ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _adduiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    private var updatenotesJob: Job? = null
    fun updateNote(notePad: NotePad) {
        updatenotesJob?.cancel()
        updatenotesJob = viewModelScope.launch {
            repository.updateNote(notePad).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _updateuiState.update {
                            it.copy(
                                isLoading = true,
                                message = ""
                            )
                        }
                    }
                    is Resource.Success -> {
                        _updateuiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.data ?: ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _updateuiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }


    private var notesGetJob: Job? = null
    fun getNotes() {
        notesGetJob?.cancel()
        notesGetJob = viewModelScope.launch {
            repository.getNotes().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                message = "",
                                notesList = result.data ?: emptyList()
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                message = result.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    private var deleteNotesJob: Job? = null
    fun deleteNote(notePad: NotePad) {
        deleteNotesJob?.cancel()
        deleteNotesJob = viewModelScope.launch {
            repository.deleteNote(notePad).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                message = ""
                            )
                        }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.data ?: ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                message = result.message ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearState() {
        _adduiState.update {
            it.copy(
                isLoading = false,
                message = ""
            )
        }
        _updateuiState.update {
            it.copy(
                isLoading = false,
                message = ""
            )
        }
    }
}