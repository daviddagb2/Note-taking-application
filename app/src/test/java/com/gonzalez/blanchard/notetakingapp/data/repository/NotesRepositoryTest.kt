package com.gonzalez.blanchard.notetakingapp.data.repository

import com.gonzalez.blanchard.notetakingapp.data.database.dao.NotesDao
import com.gonzalez.blanchard.notetakingapp.data.database.entities.NoteEntity
import com.gonzalez.blanchard.notetakingapp.data.database.mappers.toDatabase
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.utils.exceptions.NoteNotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NotesRepositoryTest {

    private lateinit var notesRepository: NotesRepository
    private val notesDao: NotesDao = mockk()

    @Before
    fun setup() {
        notesRepository = NotesRepository(notesDao)
    }

    @Test
    fun `getAllNotes should return a list of notes`() = runBlocking {
        // Configure the mock behavior for dao.getAll()
        val mockNoteEntity1 = NoteEntity(
            id = 1,
            title = "Sample Note 1",
            content = "This is the content of Sample Note 1.",
            date = "November 21, 2023",
            image = "sample_image_1.jpg",
            isFavourite = true,
            isArchived = false,
            color = "#FF5733",
            reminder = "Reminder for Sample Note 1",
            timestamp = 1637516400
        )

        val mockNoteEntity2 = NoteEntity(
            id = 2,
            title = "Sample Note 2",
            content = "This is the content of Sample Note 2.",
            date = "November 22, 2023",
            image = "sample_image_2.jpg",
            isFavourite = false,
            isArchived = true,
            color = "#3399FF",
            reminder = "Reminder for Sample Note 2",
            timestamp = 1637602800
        )

        val mockNotes = listOf(mockNoteEntity1, mockNoteEntity2)
        coEvery { notesDao.getAll() } returns mockNotes

        // Call the repository method
        val result = notesRepository.getAllNotes()

        assertTrue(result is List<*>)
    }

    @Test
    fun `getNote should return a valid note`() = runBlocking {
        // Given: Configure the mock behavior for dao.getDetail()
        val noteId = 1L
        val mockNoteEntity = NoteEntity(
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
        coEvery { notesDao.getDetail(noteId) } returns listOf(mockNoteEntity)

        // When: Call the repository method
        val result = notesRepository.getNote(noteId)

        // Then: Assert the result
        // Verify that the result matches your expectations
        assertEquals(noteId, result.id)
        assertEquals("Sample Note", result.title)
        assertEquals("This is the content of the note.", result.content)
    }


    @Test
    fun `getNote should throw NoteNotFoundException for an invalid ID`() = runBlocking {
        // Configure the mock behavior for dao.getDetail() to throw NoteNotFoundException
        val invalidNoteId = -1L
        coEvery { notesDao.getDetail(invalidNoteId) } throws NoteNotFoundException("Note not found")

        // Call the repository method and expect an exception
        try {
            notesRepository.getNote(invalidNoteId)
            // Fail the test if an exception is not thrown
            assert(false) { "Expected NoteNotFoundException" }
        } catch (e: NoteNotFoundException) {
            // Success, the exception was thrown
        }
    }

    @Test
    fun `insertNote should add a note to the database`() = runBlocking {
        // Given: Configure the mock behavior for dao.insert()
        val mockNoteModel = NoteModel(
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
        coEvery { notesDao.insert(any()) } returns Unit

        // When: Call the repository method
        notesRepository.insertNote(mockNoteModel)

        // Then: Verify that dao.insert() was called with the expected input
        coVerify(exactly = 1) { notesDao.insert(mockNoteModel.toDatabase()) }
    }


    @Test
    fun `updateNote should update an existing note in the database`() = runBlocking {
        // Given: Configure the mock behavior for dao.update()
        val mockNoteModel = NoteModel(
            id = 1,
            title = "Updated Note",
            content = "Updated content of the note.",
            date = "November 22, 2023",
            image = "updated_image.jpg",
            isFavourite = false,
            isArchived = true,
            color = "#3399FF",
            reminder = "Updated reminder for the note",
            timestamp = 1637602800
        )
        coEvery { notesDao.update(any()) } returns Unit

        // When: Call the repository method
        notesRepository.updateNote(mockNoteModel)

        // Then: Verify that dao.update() was called with the expected input
        coVerify(exactly = 1) { notesDao.update(mockNoteModel.toDatabase()) }
    }


    @Test
    fun `deleteNote should delete an existing note from the database`() = runBlocking {
        // Given: Configure the mock behavior for dao.delete()
        val noteIdToDelete = 1L
        val mockNoteModel = NoteModel(
            id = noteIdToDelete,
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
        coEvery { notesDao.delete(noteIdToDelete) } returns Unit

        // When: Call the repository method
        notesRepository.deleteNote(mockNoteModel)

        // Then: Verify that dao.delete() was called with the expected input
        coVerify(exactly = 1) { notesDao.delete(noteIdToDelete) }
    }

}