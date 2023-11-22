package com.gonzalez.blanchard.notetakingapp.presentation.note

import androidx.lifecycle.viewModelScope
import com.gonzalez.blanchard.notetakingapp.core.BaseViewModel
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.domain.usecases.GetNoteListUseCase
import com.gonzalez.blanchard.notetakingapp.domain.usecases.GetNoteUseCase
import com.gonzalez.blanchard.notetakingapp.domain.usecases.InsertNoteUseCase
import com.gonzalez.blanchard.notetakingapp.presentation.main.MainActivityActions
import com.gonzalez.blanchard.notetakingapp.presentation.main.MainActivityStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
) : BaseViewModel() {

    private val _actions = Channel<NoteActions>()
    val actions = _actions.receiveAsFlow()

    private val _status = Channel<NoteStatus>()
    val status = _status.receiveAsFlow()

    fun viewCreated() {
        viewModelScope.launch {

        }
    }

    private fun getNoteDetail(noteId: Int) {
        executeUseCase(action = {
            _status.send(NoteStatus.IsLoading)
            val result = getNoteUseCase.execute(noteId)
            _actions.send(NoteActions.LoadNote(result))
        }, exceptionHandler = {
            _status.send(NoteStatus.Error(it.toString()))
        }, finallyHandler = {
            _status.send(NoteStatus.Idle)
        })
    }

    fun onSaveNoteClicked(note: NoteModel) {
        executeUseCase(action = {
            _status.send(NoteStatus.IsLoading)
            insertNoteUseCase.execute(note)
            _status.send(NoteStatus.Idle)
        }, exceptionHandler = {
            _status.send(NoteStatus.Error(it.toString()))
        }, finallyHandler = {
            _status.send(NoteStatus.Idle)
        })
    }

}
