package com.gonzalez.blanchard.notetakingapp.presentation.note

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.gonzalez.blanchard.notetakingapp.databinding.ActivityNoteBinding
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.utils.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Method
        noteViewModel.viewCreated()

        initActionsListener()
        initStatusListener()
        initButtonsListeners()
    }

    private fun initButtonsListeners() {
        binding.btnSaveNote.setOnClickListener {
            val noteModel = NoteModel(
                id = 0,
                title = binding.editTextTitle.text.toString(),
                content = binding.editTextContent.text.toString(),
                date = "",
                image = "",
                isFavourite = false,
                isArchived = false,
                color = "",
                reminder = "",
            )
            noteViewModel.onSaveNoteClicked(noteModel)
        }
    }

    private fun initActionsListener() {
        this.launchAndCollect(noteViewModel.actions) { actions ->
            when (actions) {
                is NoteActions.LoadNote -> loadNote(actions.note.id)
                is NoteActions.SaveNote -> saveNote()
            }
        }
    }

    private fun initStatusListener() {
        this.launchAndCollect(noteViewModel.status) { status ->
            when (status) {
                NoteStatus.IsLoading -> showLoader()
                NoteStatus.Idle -> hideLoader()
                is NoteStatus.Error -> showError(status.error)
            }
        }
    }

    private fun showLoader() {
    }
    private fun hideLoader() {
    }

    private fun showError(error: String) {
    }

    private fun loadNote(noteid: Long) {
    }

    private fun saveNote() {
    }
}
