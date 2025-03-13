package com.example.apinote.app.domain.usecases

import androidx.paging.PagingData
import com.example.apinote.app.data.api.repository.RickMortyRepository
import com.example.apinote.app.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val repository: RickMortyRepository
){
    operator fun invoke(): Flow<PagingData<CharacterModel>>{
        return repository.getAllCharacters()
    }
}