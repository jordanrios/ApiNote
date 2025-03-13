package com.example.apinote.app.ui.viewmodels.api

import com.example.apinote.app.domain.models.CharacterModel

interface DetailUiState {
    object Loading : DetailUiState
    data class Error(val throwable: Throwable) : DetailUiState
    data class Success(val character: CharacterModel) : DetailUiState
}