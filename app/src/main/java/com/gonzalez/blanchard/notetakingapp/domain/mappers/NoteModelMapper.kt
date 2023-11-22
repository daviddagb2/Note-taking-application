package com.gonzalez.blanchard.notetakingapp.domain.mappers

import com.gonzalez.blanchard.notetakingapp.data.database.entities.NoteEntity
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel

fun NoteEntity.toDomain(): NoteModel =
    NoteModel(
        id = this.id,
        title = this.title ?: "",
        content = this.content ?: "",
        date = this.date ?: "",
        image = this.image ?: "",
        isFavourite = this.isFavourite ?: false,
        isArchived = this.isArchived ?: false,
        color = this.color ?: "",
        reminder = this.reminder ?: "",
        timestamp = this.timestamp ?: 0L,
    )
