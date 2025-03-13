package com.example.apinote.app.domain.usecases

import com.example.apinote.app.data.database.repository.NoteRepository
import com.example.apinote.app.domain.models.NoteModel
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(noteModel: NoteModel) {  //You use the class as a function with invoke overloading it with operator
        noteRepository.add(noteModel)
    }

}