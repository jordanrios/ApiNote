package com.example.apinote.app.domain.models

data class CharacterModel(
    val id: Int,
    val name: String,
    val isAlive: Boolean,
    val species: String,
    val image: String
)
