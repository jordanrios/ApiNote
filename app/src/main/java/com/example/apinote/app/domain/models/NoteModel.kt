package com.example.apinote.app.domain.models


data class NoteModel(
    val id: Int = System.currentTimeMillis().hashCode(),
    val note: String,
    var selected: Boolean = false
)
