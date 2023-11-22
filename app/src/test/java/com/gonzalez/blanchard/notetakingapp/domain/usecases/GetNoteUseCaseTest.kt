package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.utils.exceptions.NoteNotFoundException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetNoteUseCaseTest{

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    private lateinit var getNoteUseCase: GetNoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getNoteUseCase = GetNoteUseCase(notesRepository)
    }

    @Test
    fun `when a valid ID is provided, the useCase returns the note`() = runBlocking {
        // Given: Configure the repository behavior to return a fictional note
        val noteId = 1L
        coEvery { notesRepository.getNote(noteId) } returns NoteModel(
            id = noteId,
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

        // When: Execute the use case with the provided ID
        val result = getNoteUseCase.execute(noteId)

        // Then: Verify that the returned note is as expected
        assertEquals(noteId, result.id)
        assertEquals("Sample Note", result.title)
        assertEquals("This is the content of the note.", result.content)
    }


    @Test
    fun `when an invalid ID is provided, the useCase throws NoteNotFoundException`() = runBlocking {
        // Given: Configure the repository behavior to throw an exception when an invalid ID is provided
        val invalidNoteId = -1L
        coEvery { notesRepository.getNote(invalidNoteId) } throws NoteNotFoundException("Note not found")

        try {
            // When: Execute the use case with an invalid ID
            getNoteUseCase.execute(invalidNoteId)

            // Then: Fail the test if an exception is not thrown
            fail("Expected NoteNotFoundException, but no exception was thrown")
        } catch (e: NoteNotFoundException) {
            // This is expected behavior
        }
    }

}