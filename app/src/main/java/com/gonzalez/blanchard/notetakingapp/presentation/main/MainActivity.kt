package com.gonzalez.blanchard.notetakingapp.presentation.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.gonzalez.blanchard.notetakingapp.R
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
                is MainActivityStatus.Error -> showError(status.error)
                MainActivityStatus.Idle -> hideLoader()
                MainActivityStatus.IsLoading ->  showLoader()
            }
        }
    }

    private fun initButtonsListeners() {
        binding.fabAddNote.setOnClickListener {
            mainActivityViewModel.onAddNoteClicked()
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val searchText = s?.toString() ?: ""
                mainActivityViewModel.onSearchTextChanged(searchText)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun goToNoteDetail(note: NoteModel) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra(ITEM_NOTE_CLICKED, note.id.toString())
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            findViewById(R.id.tvTitle),
            "transitionTitle",
        )
        startActivity(intent, options.toBundle())
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
        binding.rvNotes.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
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
