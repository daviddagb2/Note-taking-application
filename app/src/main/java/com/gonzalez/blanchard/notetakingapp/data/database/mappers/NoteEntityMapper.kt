package com.gonzalez.blanchard.notetakingapp.data.database.mappers

import com.gonzalez.blanchard.notetakingapp.data.database.entities.NoteEntity
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel

fun NoteModel.toDatabase(): NoteEntity =
    NoteEntity(
        id = this.id,
        title = this.title,
        content = this.content,
        date = this.date,
        image = this.image,
        isFavourite = this.isFavourite,
        isArchived = this.isArchived,
        color = this.color,
        reminder = this.reminder,
        timestamp = this.timestamp,
    )
