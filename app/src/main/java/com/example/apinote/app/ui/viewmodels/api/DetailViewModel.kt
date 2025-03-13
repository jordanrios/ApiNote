package com.example.apinote.app.ui.viewmodels.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apinote.app.domain.usecases.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)  //Initialize loading
    val uiState: StateFlow<DetailUiState> = _uiState

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            try {
                val character = getCharacterByIdUseCase(characterId)
                _uiState.value = DetailUiState.Success(character)
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e)
            }
        }
    }
}