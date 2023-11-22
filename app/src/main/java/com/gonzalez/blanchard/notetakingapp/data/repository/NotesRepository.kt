package com.gonzalez.blanchard.notetakingapp.data.repository

import com.gonzalez.blanchard.notetakingapp.data.database.dao.NotesDao
import com.gonzalez.blanchard.notetakingapp.data.database.mappers.toDatabase
import com.gonzalez.blanchard.notetakingapp.domain.mappers.toDomain
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val dao: NotesDao,
) {
    suspend fun getAllNotes(): List<NoteModel> {
        val response = dao.getAll()
        return response.map { it.toDomain() }
    }

    suspend fun getNote(noteid: Int): NoteModel {
        val response = dao.getDetail(noteid)
        return response.map { it.toDomain() }.first()
    }

    suspend fun insertNote(noteModel: NoteModel) {
        dao.insert(noteModel.toDatabase())
    }
}
