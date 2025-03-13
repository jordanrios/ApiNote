package com.example.apinote.app.ui.viewmodels.notes

import com.example.apinote.app.domain.models.NoteModel

sealed interface NotesUiState {
    object Loading : NotesUiState
    data class Error(val throwable: Throwable) : NotesUiState
    data class Success(val notes: List<NoteModel>) : NotesUiState

}