package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import javax.inject.Inject


class GetNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) : BaseUseCase<Int, NoteModel>() {

    override suspend fun useCaseFunction(input: Int): NoteModel {
        return notesRepository.getNote(input)
    }
}