package com.example.apinote.app.data.api.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.apinote.app.core.toData
import com.example.apinote.app.data.api.services.RickMortyApiService
import com.example.apinote.app.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickMortyRepository @Inject constructor(val api: RickMortyApiService) {

    companion object {
        const val MAX_ITEMS = 10
        const val PREFETCH_ITEMS = 3
    }

    fun getAllCharacters(): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = { CharacterPagingSource(api) }
        ).flow
    }

    suspend fun getCharacterById(id: Int): CharacterModel {
        return api.getCharacterById(id).toData()
    }

}