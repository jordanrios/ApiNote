package com.example.apinote.app.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey
    val id: Int,
    val note: String,
    val selected: Boolean = false
)
