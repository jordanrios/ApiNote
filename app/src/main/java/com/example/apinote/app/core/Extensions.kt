package com.example.apinote.app.core

import com.example.apinote.app.data.api.response.CharacterResponse
import com.example.apinote.app.data.database.entities.NoteEntity
import com.example.apinote.app.domain.models.CharacterModel
import com.example.apinote.app.domain.models.NoteModel


fun NoteModel.toData(): NoteEntity {
    return NoteEntity(this.id, this.note, this.selected)
}


fun CharacterResponse.toData(): CharacterModel {
    return CharacterModel(
        id = id,
        name = name,
        isAlive = status == "Alive",
        species = species,
        image = image
    )
}







