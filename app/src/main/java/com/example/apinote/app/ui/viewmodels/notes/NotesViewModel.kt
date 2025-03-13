package com.example.apinote.app.ui.viewmodels.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apinote.app.domain.models.NoteModel
import com.example.apinote.app.domain.usecases.AddNoteUseCase
import com.example.apinote.app.domain.usecases.DeleteNoteUseCase
import com.example.apinote.app.domain.usecases.GetNoteUseCase
import com.example.apinote.app.domain.usecases.UpdateNoteUseCase
import com.example.apinote.app.ui.viewmodels.notes.NotesUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    getNoteUseCase: GetNoteUseCase
) : ViewModel() {

    val uiState: StateFlow<NotesUiState> = getNoteUseCase().map(::Success)
        .catch { NotesUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesUiState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog


    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }


    fun onNotesCreated(note: String) {
        _showDialog.value = false
        viewModelScope.launch {
            addNoteUseCase(NoteModel(note = note))
        }
    }
    fun onNoteUpdated(updatedNoteModel: NoteModel) {
        viewModelScope.launch {
            updateNoteUseCase(updatedNoteModel)
        }
    }
       fun onItemRemove(noteModel: NoteModel) {
        viewModelScope.launch {
            deleteNoteUseCase(noteModel)
        }
    }


    fun onCheckBoxSelected(noteModel: NoteModel) {
        viewModelScope.launch {
            updateNoteUseCase(noteModel.copy(selected = !noteModel.selected))
        }
    }
}
