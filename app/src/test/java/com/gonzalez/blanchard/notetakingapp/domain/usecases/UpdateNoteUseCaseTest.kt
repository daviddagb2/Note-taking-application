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


class UpdateNoteUseCaseTest {

    private lateinit var updateNoteUseCase: UpdateNoteUseCase

    private val noteRepository: NotesRepository = mockk()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        updateNoteUseCase = UpdateNoteUseCase(noteRepository)
    }

    @Test
    fun `when a valid note is provided, the useCase updates the note`() = runBlocking {
        // Given: Configure the repository behavior to succeed when updating a note
        val noteToUpdate = NoteModel(
            id = 1,
            title = "Updated Note",
            content = "This is the updated content of the note.",
            date = "November 21, 2023",
            image = "updated_image.jpg",
            isFavourite = true,
            isArchived = false,
            color = "#FF5733",
            reminder = "Updated reminder for the note",
            timestamp = 1637516400
        )

        coEvery { noteRepository.updateNote(noteToUpdate) } returns Unit

        // When: Execute the use case with the provided updated note
        updateNoteUseCase.execute(noteToUpdate)

        // Then: Verify that the repository is called to update the note
        coVerify(exactly = 1) { noteRepository.updateNote(noteToUpdate) }
    }
}