package com.gonzalez.blanchard.notetakingapp.domain.models

data class NoteModel(
    val id: Long,
    val title: String,
    val content: String,
    val date: String,
    val image: String,
    val isFavourite: Boolean,
    val isArchived: Boolean,
    val color: String,
    val reminder: String,
)
