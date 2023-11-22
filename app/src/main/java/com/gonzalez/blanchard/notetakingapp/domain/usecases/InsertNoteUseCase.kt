package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) : BaseUseCase<NoteModel, Unit>() {

    override suspend fun useCaseFunction(input: NoteModel) {
        return notesRepository.insertNote(input)
    }
}
