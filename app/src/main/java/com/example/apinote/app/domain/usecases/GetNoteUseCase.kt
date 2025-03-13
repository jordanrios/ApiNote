package com.example.apinote.app.domain.usecases

import com.example.apinote.app.data.database.repository.NoteRepository
import com.example.apinote.app.domain.models.NoteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    operator fun invoke(): Flow<List<NoteModel>> = noteRepository.notes
}