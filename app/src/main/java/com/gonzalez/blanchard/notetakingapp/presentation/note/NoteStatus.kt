package com.gonzalez.blanchard.notetakingapp.presentation.note

sealed class NoteStatus {
    class Error(val error: String) : NoteStatus()
    object IsLoading : NoteStatus()
    object Idle : NoteStatus()
}
