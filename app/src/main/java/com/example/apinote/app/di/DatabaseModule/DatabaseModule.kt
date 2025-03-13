package com.example.apinote.app.di.DatabaseModule

import android.content.Context
import androidx.room.Room
import com.example.apinote.app.core.utils.Constants
import com.example.apinote.app.data.database.TodoDatabase
import com.example.apinote.app.data.database.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideNoteDao(todoDatabase: TodoDatabase): NoteDao {
        return todoDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(appContext, TodoDatabase::class.java, Constants.NOTE_DATABASE).build()
    }


}