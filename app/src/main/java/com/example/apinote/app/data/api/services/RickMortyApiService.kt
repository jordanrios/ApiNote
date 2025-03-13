package com.example.apinote.app.data.api.services

import com.example.apinote.app.data.api.response.CharacterResponse
import com.example.apinote.app.data.api.response.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickMortyApiService {

    @GET("/api/character/")
    suspend fun getCharacters(@Query("page") page: Int): ResponseWrapper

    @GET("/api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterResponse

}