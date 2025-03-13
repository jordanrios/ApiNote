package com.example.apinote.app.ui.viewmodels.api

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.apinote.app.data.api.repository.RickMortyRepository
import com.example.apinote.app.domain.models.CharacterModel
import com.example.apinote.app.domain.usecases.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModel() {

    val characters: Flow<PagingData<CharacterModel>> = getAllCharactersUseCase()

}