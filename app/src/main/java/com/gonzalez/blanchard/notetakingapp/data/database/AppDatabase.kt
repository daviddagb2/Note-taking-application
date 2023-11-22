package com.gonzalez.blanchard.notetakingapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gonzalez.blanchard.notetakingapp.data.database.dao.NotesDao
import com.gonzalez.blanchard.notetakingapp.data.database.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}
