package com.gonzalez.blanchard.notetakingapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gonzalez.blanchard.notetakingapp.data.database.entities.NoteEntity

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes ORDER BY id ASC")
    suspend fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getDetail(noteId: Int): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :search || '%'")
    suspend fun searchNote(search: String): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<NoteEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: NoteEntity)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()
}
