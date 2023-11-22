package com.gonzalez.blanchard.notetakingapp.presentation.main

import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel

sealed class MainActivityActions {
    class GoToNoteDetail(val note: NoteModel) : MainActivityActions()
    class ShowNotes(val notes: List<NoteModel>) : MainActivityActions()
    object ShowEmptyNotes : MainActivityActions()
    object GoToAddNote : MainActivityActions()
}
