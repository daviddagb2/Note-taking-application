package com.gonzalez.blanchard.notetakingapp.data.repository

import com.gonzalez.blanchard.notetakingapp.data.database.dao.NotesDao
import com.gonzalez.blanchard.notetakingapp.data.database.mappers.toDatabase
import com.gonzalez.blanchard.notetakingapp.domain.mappers.toDomain
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.utils.exceptions.NoteNotFoundException
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val dao: NotesDao,
) {
    suspend fun getAllNotes(): List<NoteModel> {
        val response = dao.getAll()
        return response.map { it.toDomain() }
    }

    suspend fun searchNotes(searchName: String): List<NoteModel> {
        val response = dao.searchNote(searchName)
        return response.map { it.toDomain() }
    }

    suspend fun getNote(noteid: Long): NoteModel {
        val response = dao.getDetail(noteid)
        val noteEntity = response.map { it.toDomain() }.firstOrNull()
            ?: throw NoteNotFoundException("Note with ID $noteid not found.")

        return noteEntity
    }

    suspend fun insertNote(noteModel: NoteModel) {
        dao.insert(noteModel.toDatabase())
    }

    suspend fun updateNote(noteModel: NoteModel) {
        dao.update(noteModel.toDatabase())
    }

    suspend fun deleteNote(noteModel: NoteModel) {
        dao.delete(noteModel.id)
    }
}
