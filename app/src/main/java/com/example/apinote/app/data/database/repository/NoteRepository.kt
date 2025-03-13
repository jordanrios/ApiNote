package com.example.apinote.app.data.database.repository

import com.example.apinote.app.core.toData
import com.example.apinote.app.data.database.dao.NoteDao
import com.example.apinote.app.domain.models.NoteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    val notes: Flow<List<NoteModel>> =
        noteDao.getNotes().map { items -> items.map { NoteModel(it.id, it.note, it.selected) } }

    suspend fun add(noteModel: NoteModel) {
        noteDao.addNotes(noteModel.toData())
    }

    suspend fun update(noteModel: NoteModel) {
        noteDao.updateNotes(noteModel.toData())
    }

    suspend fun delete(noteModel: NoteModel) {
        noteDao.deleteNotes(noteModel.toData())
    }
}

