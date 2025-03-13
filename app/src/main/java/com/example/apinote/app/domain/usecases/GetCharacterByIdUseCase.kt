package com.example.apinote.app.domain.usecases

import com.example.apinote.app.data.api.repository.RickMortyRepository
import com.example.apinote.app.domain.models.CharacterModel
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val repository: RickMortyRepository
) {
    suspend operator fun invoke(characterId: Int): CharacterModel {
        return repository.getCharacterById(characterId)
    }
}