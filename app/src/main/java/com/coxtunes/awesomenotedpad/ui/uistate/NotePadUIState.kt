package com.coxtunes.awesomenotedpad.ui.uistate

import com.coxtunes.awesomenotedpad.data.dto.NotePad


data class NotePadUIState(
    val isLoading: Boolean = false,
    val message: String = "",
    val notesList: List<NotePad> = emptyList()
)

