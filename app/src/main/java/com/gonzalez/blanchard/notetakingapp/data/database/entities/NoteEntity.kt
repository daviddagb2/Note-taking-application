package com.gonzalez.blanchard.notetakingapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "is_favourite") val isFavourite: Boolean?,
    @ColumnInfo(name = "is_archived") val isArchived: Boolean?,
    @ColumnInfo(name = "color") val color: String?,
    @ColumnInfo(name = "reminder") val reminder: String?,
    @ColumnInfo(name = "timestamp") val timestamp: Long?,
)
