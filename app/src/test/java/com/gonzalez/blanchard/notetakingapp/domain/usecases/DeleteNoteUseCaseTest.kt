package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.utils.exceptions.NoteNotFoundException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class DeleteNoteUseCaseTest {

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    private lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        deleteNoteUseCase = DeleteNoteUseCase(notesRepository)
    }

    @Test
    fun `when a valid note is provided, the useCase deletes the note`() = runBlocking {
        // Given: Configure the repository behavior to succeed when deleting a note
        val noteToDelete = NoteModel(
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

        coEvery { notesRepository.deleteNote(noteToDelete) } returns Unit

        // When: Execute the use case with the provided note
        deleteNoteUseCase.execute(noteToDelete)

        // Then: Verify that the repository is called to delete the note
        coVerify(exactly = 1) { notesRepository.deleteNote(noteToDelete) }
    }

    @Test(expected = NoteNotFoundException::class)
    fun `when a note is not found, the useCase throws NoteNotFoundException`() = runBlocking {
        // Given: Configure the repository behavior to throw NoteNotFoundException
        val noteToDelete = NoteModel(
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

        coEvery { notesRepository.deleteNote(noteToDelete) } throws NoteNotFoundException("Note not found")

        // When: Execute the use case with the provided note
        deleteNoteUseCase.execute(noteToDelete)
    }
}