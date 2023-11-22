package com.gonzalez.blanchard.notetakingapp.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.gonzalez.blanchard.notetakingapp.databinding.ActivityMainBinding
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.presentation.adapters.NotesAdapter
import com.gonzalez.blanchard.notetakingapp.presentation.note.NoteActivity
import com.gonzalez.blanchard.notetakingapp.utils.ITEM_NOTE_CLICKED
import com.gonzalez.blanchard.notetakingapp.utils.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Method
        mainActivityViewModel.viewCreated()

        initActionsListener()
        initStatusListener()
        initButtonsListeners()
    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.viewCreated()
    }

    private fun initActionsListener() {
        this.launchAndCollect(mainActivityViewModel.actions) { actions ->
            when (actions) {
                is MainActivityActions.GoToNoteDetail -> goToNoteDetail(actions.note)
                is MainActivityActions.ShowNotes -> showNotesList(actions.notes)
                MainActivityActions.GoToAddNote -> goToAddNote()
                MainActivityActions.ShowEmptyNotes -> showEmptyList()
            }
        }
    }

    private fun initStatusListener() {
        this.launchAndCollect(mainActivityViewModel.status) { status ->
            when (status) {
                MainActivityStatus.IsLoading -> showLoader()
                MainActivityStatus.Idle -> hideLoader()
                is MainActivityStatus.Error -> showError(status.error)
            }
        }
    }

    private fun initButtonsListeners() {
        binding.fabAddNote.setOnClickListener {
            mainActivityViewModel.onAddNoteClicked()
        }
    }

    private fun goToNoteDetail(note: NoteModel) {
        Intent(this, NoteActivity::class.java).apply {
            putExtra(ITEM_NOTE_CLICKED, note.id)
        }.let {
            startActivity(it)
        }
    }

    private fun goToAddNote() {
        Intent(this, NoteActivity::class.java).apply {
        }.let {
            startActivity(it)
        }
    }

    private fun showNotesList(notes: List<NoteModel>) {
        binding.rvNotes.isVisible = true
        val notesAdapter = NotesAdapter(notes) {
            mainActivityViewModel.onNoteClicked(it)
        }
        binding.rvNotes.layoutManager = GridLayoutManager(this, 2)
        binding.rvNotes.adapter = notesAdapter
    }

    private fun showEmptyList() {
        binding.llEmptyNotes.isVisible = true
    }

    private fun showLoader() {
        binding.loaderIndicator.isVisible = true
        binding.llEmptyNotes.isVisible = false
    }

    private fun hideLoader() {
        binding.loaderIndicator.isVisible = false
    }

    private fun showError(error: String) {
    }
}
