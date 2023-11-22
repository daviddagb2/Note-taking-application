package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) : BaseUseCase<String, List<NoteModel>>() {

    override suspend fun useCaseFunction(input: String): List<NoteModel> {
        return notesRepository.searchNotes(input)
    }
}