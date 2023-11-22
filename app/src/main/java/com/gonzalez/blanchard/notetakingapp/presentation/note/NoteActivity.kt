package com.gonzalez.blanchard.notetakingapp.presentation.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.gonzalez.blanchard.notetakingapp.databinding.ActivityNoteBinding
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.utils.ITEM_NOTE_CLICKED
import com.gonzalez.blanchard.notetakingapp.utils.launchAndCollect
import com.gonzalez.blanchard.notetakingapp.utils.serializable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transitionName = "transitionTitle"
        val sharedElement = binding.tvTitle
        ViewCompat.setTransitionName(sharedElement, transitionName)

        // Get the bundle
        intent?.extras?.serializable<String>(ITEM_NOTE_CLICKED)?.let { noteId ->
            noteViewModel.setId(noteId.toLong())
            noteViewModel.getNoteDetail(noteId)
        } ?: run {
            noteViewModel.setId(0L)
            noteViewModel.viewCreated()
        }

        initActionsListener()
        initStatusListener()
        initButtonsListeners()
    }

    private fun initButtonsListeners() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnDelete.setOnClickListener {
            noteViewModel.onDeleteNote()
            noteViewModel.setShouldSaveOnBack(false)
            onBackPressed()
        }

        binding.tvTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                noteViewModel.setTitle(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.tvContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                noteViewModel.setContent(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun initActionsListener() {
        this.launchAndCollect(noteViewModel.actions) { actions ->
            when (actions) {
                is NoteActions.LoadNote -> loadNote(actions.note)
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
        binding.progressBar.isVisible = true
    }
    private fun hideLoader() {
        binding.progressBar.isVisible = false
    }

    private fun showError(error: String) {
    }

    private fun loadNote(note: NoteModel) {
        binding.tvTitle.setText(note.title)
        binding.tvContent.setText(note.content)
        binding.textViewDateCharacterCount.text = note.date
    }

    private fun saveNote() {
    }

    override fun onBackPressed() {
        if (noteViewModel.shouldSaveOnBack) {
            noteViewModel.setTitle(binding.tvTitle.text.toString())
            noteViewModel.setContent(binding.tvContent.text.toString())
            noteViewModel.onSaveNote()
        }
        super.onBackPressed()
    }
}
