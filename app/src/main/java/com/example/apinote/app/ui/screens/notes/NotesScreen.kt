package com.example.apinote.app.ui.screens.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.apinote.app.domain.models.NoteModel
import com.example.apinote.app.ui.viewmodels.notes.NotesUiState
import com.example.apinote.app.ui.viewmodels.notes.NotesViewModel

@Composable
fun NotesScreen(notesViewModel: NotesViewModel = hiltViewModel()) {
    val showDialog: Boolean by notesViewModel.showDialog.observeAsState(false)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<NotesUiState>(
        initialValue = NotesUiState.Loading,
        key1 = lifecycle,
        key2 = notesViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            notesViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        NotesUiState.Loading -> {
            LoadingScreen()
        }

        is NotesUiState.Error -> {
            ErrorScreen()
        }

        is NotesUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                //Color degraded
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF1A1A1A).copy(alpha = 0.8f),
                                    Color(0xFF333333).copy(alpha = 0.7f),
                                    Color(0xFF0066CC).copy(alpha = 0.5f),
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
                NotesList(notes = (uiState as NotesUiState.Success).notes, notesViewModel)
                FabDialog(Modifier.align(Alignment.BottomEnd), notesViewModel)
                AddNotesDialog(
                    show = showDialog,
                    onDismiss = { notesViewModel.onDialogClose() },
                    onNoteAdded = { notesViewModel.onNotesCreated(it) }
                )
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(64.dp)
        )
    }
}

@Composable
fun ErrorScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "An error occurred, please try again later.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )
    }
}

@Composable
fun NotesList(notes: List<NoteModel>, notesViewModel: NotesViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 80.dp)
    ) {
        items(notes, key = { it.id }) { note ->
            ItemNote(note, notesViewModel)
        }
    }
}

@Composable
fun ItemNote(noteModel: NoteModel, notesViewModel: NotesViewModel) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var showEditDialog by rememberSaveable { mutableStateOf(false) }
    var editedNote by rememberSaveable { mutableStateOf(noteModel.note) }

    // Card UI for displaying the note
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showDeleteDialog = true },
                    onTap = { showEditDialog = true }
                )
            },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = noteModel.note,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onSurface
            )
            Checkbox(
                checked = noteModel.selected,
                onCheckedChange = { notesViewModel.onCheckBoxSelected(noteModel) },
                colors = CheckboxDefaults.colors(checkmarkColor = MaterialTheme.colorScheme.primary)
            )
        }
    }
    // Delete confirmation dialog
    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                notesViewModel.onItemRemove(noteModel)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
    // Edit note dialog
    if (showEditDialog) {
        EditNoteDialog(
            initialNote = editedNote,
            onSave = { newNote ->
                if (newNote.isNotBlank()) {
                    notesViewModel.onNoteUpdated(noteModel.copy(note = newNote))
                    editedNote = newNote
                }
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false }
        )
    }
}


@Composable
fun FabDialog(modifier: Modifier, notesViewModel: NotesViewModel) {
    FloatingActionButton(
        onClick = { notesViewModel.onShowDialogClick() },
        modifier = modifier.padding(16.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add note")
    }
}

@Composable
fun AddNotesDialog(show: Boolean, onDismiss: () -> Unit, onNoteAdded: (String) -> Unit) {
    var myNote by remember { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = {
            myNote = ""
            onDismiss()
        }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                Text(
                    text = "Add Note",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = myNote,
                    onValueChange = { myNote = it },
                    label = { Text("Write your note") },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (myNote.isNotBlank()) {
                            onNoteAdded(myNote)
                            myNote = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Add", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}

@Composable
fun EditNoteDialog(initialNote: String, onSave: (String) -> Unit, onDismiss: () -> Unit) {
    var editedNote by rememberSaveable { mutableStateOf(initialNote) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                ),
        ) {
            Text(
                text = "Edit Note",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = editedNote,
                onValueChange = { editedNote = it },
                label = { Text("Write your note") },
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onSave(editedNote)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Save", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Delete note") },
        text = { Text("Are you sure you want to delete this note?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}