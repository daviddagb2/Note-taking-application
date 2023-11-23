package com.gonzalez.blanchard.notetakingapp.presentation.note

import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel

sealed class NoteActions {
    class SaveNote(val note: NoteModel) : NoteActions()
    class LoadNote(val note: NoteModel) : NoteActions()
    class SetDate(val date: String) : NoteActions()
}
