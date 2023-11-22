package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteListUseCaseTest {

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    private lateinit var getNoteListUseCase: GetNoteListUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getNoteListUseCase = GetNoteListUseCase(notesRepository)
    }

    @Test
    fun `when the useCase is executed then the repository is called with an Empty List`() = runBlocking {
        // Given
        coEvery { notesRepository.getAllNotes() } returns emptyList()

        // When
        getNoteListUseCase.execute(Unit)

        // Then
        coVerify(exactly = 1) { notesRepository.getAllNotes() }
    }

    @Test
    fun `when the useCase is executed then the repository is called`() = runBlocking {
        coEvery { notesRepository.getAllNotes() } returns listOf(
            NoteModel(
                id = 1,
                title = "Note 1",
                content = "This is the content of Note 1.",
                date = "November 21, 2023",
                image = "imagen1.jpg",
                isFavourite = true,
                isArchived = false,
                color = "#FF5733",
                reminder = "Recordatorio de la Nota 1",
                timestamp = 1637516400
            ),
            NoteModel(
                id = 2,
                title = "Note 2",
                content = "This is the content of Note 2.",
                date = "November 21, 2023",
                image = "imagen2.jpg",
                isFavourite = false,
                isArchived = true,
                color = "#3399FF",
                reminder = "Recordatorio de la Nota 2",
                timestamp = 1637602800
            )
        )

        // When: Use case is executed
        val result = getNoteListUseCase.execute(Unit)

        // Then: Verify that the repository is called
        coVerify(exactly = 1) { notesRepository.getAllNotes() }

        assertEquals(2, result.size)
    }
}
