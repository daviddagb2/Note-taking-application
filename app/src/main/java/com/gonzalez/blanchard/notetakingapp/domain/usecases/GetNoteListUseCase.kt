package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import javax.inject.Inject

class GetNoteListUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) : BaseUseCase<Unit, List<NoteModel>>() {

    override suspend fun useCaseFunction(input: Unit): List<NoteModel> {
        return notesRepository.getAllNotes()
    }
}
