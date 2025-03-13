package com.example.apinote.app.data.database.dao

import androidx.room.*
import com.example.apinote.app.data.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Query("SELECT * from NoteEntity")
    fun getNotes(): Flow<List<NoteEntity>>

    @Insert
    suspend fun addNotes(item: NoteEntity)

    @Update
    suspend fun updateNotes(item: NoteEntity)

    @Delete
    suspend fun deleteNotes(item: NoteEntity)
}
