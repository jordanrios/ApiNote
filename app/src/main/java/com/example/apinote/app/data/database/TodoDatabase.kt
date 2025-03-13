package com.example.apinote.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apinote.app.data.database.dao.NoteDao
import com.example.apinote.app.data.database.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}