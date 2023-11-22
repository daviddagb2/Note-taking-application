package com.gonzalez.blanchard.notetakingapp.domain.usecases

import com.gonzalez.blanchard.notetakingapp.data.repository.NotesRepository
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class SearchNotesUseCaseTest {

    @RelaxedMockK
    private lateinit var notesRepository: NotesRepository

    private lateinit var searchNotesUseCase: SearchNotesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        searchNotesUseCase = SearchNotesUseCase(notesRepository)
    }

    @Test
    fun `when a valid search query is provided, the useCase returns a list of matching notes`() = runBlocking {
        // Given: Configure the mock behavior for notesRepository.searchNotes()
        val searchQuery = "sample"
        val mockNotes = listOf(
            NoteModel(
                id = 1,
                title = "Sample Note 1",
                content = "This is a sample note 1.",
                date = "November 21, 2023",
                image = "sample_image_1.jpg",
                isFavourite = true,
                isArchived = false,
                color = "#FF5733",
                reminder = "Reminder for sample note 1",
                timestamp = 1637516400
            ),

            NoteModel(
                id = 2,
                title = "Sample Note 2",
                content = "This is a sample note 2.",
                date = "November 22, 2023",
                image = "sample_image_2.jpg",
                isFavourite = false,
                isArchived = true,
                color = "#3399FF",
                reminder = "Reminder for sample note 2",
                timestamp = 1637602800
            )
        )
        coEvery { notesRepository.searchNotes(searchQuery) } returns mockNotes

        // When: Execute the use case with the provided search query
        val result = searchNotesUseCase.execute(searchQuery)

        // Then: Verify that the use case returns the expected list of notes
        assert(result == mockNotes)
    }

    @Test
    fun `when an empty search query is provided, the useCase returns an empty list`() = runBlocking {
        // Given: Configure the mock behavior for notesRepository.searchNotes()
        val emptySearchQuery = ""
        val emptyMockNotes = emptyList<NoteModel>()
        coEvery { notesRepository.searchNotes(emptySearchQuery) } returns emptyMockNotes

        // When: Execute the use case with an empty search query
        val result = searchNotesUseCase.execute(emptySearchQuery)

        // Then: Verify that the use case returns an empty list
        assert(result.isEmpty())
    }

}