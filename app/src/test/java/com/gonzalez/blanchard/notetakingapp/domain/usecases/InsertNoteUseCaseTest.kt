package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertNoteUseCaseTest {

    private lateinit var insertNoteUseCase: InsertNoteUseCase

    private val noteRepository: NotesRepository = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        insertNoteUseCase = InsertNoteUseCase(noteRepository)
    }

    @Test
    fun `when a valid note is provided, the useCase inserts the note`() = runBlocking {
        // Given: Configure the repository behavior to succeed when inserting a note
        val noteToInsert = NoteModel(
            id = 1,
            title = "Sample Note",
            content = "This is the content of the note.",
            date = "November 21, 2023",
            image = "sample_image.jpg",
            isFavourite = true,
            isArchived = false,
            color = "#FF5733",
            reminder = "Reminder for the note",
            timestamp = 1637516400
        )

        coEvery { noteRepository.insertNote(noteToInsert) } returns Unit

        // When: Execute the use case with the provided note
        insertNoteUseCase.execute(noteToInsert)

        // Then: Verify that the repository is called to insert the note
        coVerify(exactly = 1) { noteRepository.insertNote(noteToInsert) }
    }
}