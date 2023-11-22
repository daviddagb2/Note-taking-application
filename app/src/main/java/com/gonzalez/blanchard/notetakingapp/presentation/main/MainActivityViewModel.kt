package com.gonzalez.blanchard.notetakingapp.presentation.main

import androidx.lifecycle.viewModelScope
import com.gonzalez.blanchard.notetakingapp.core.BaseViewModel
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.domain.usecases.GetNoteListUseCase
import com.gonzalez.blanchard.notetakingapp.domain.usecases.SearchNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getNoteListUseCase: GetNoteListUseCase,
    private val searchNotesUseCase: SearchNotesUseCase,
) : BaseViewModel() {

    private val _actions = Channel<MainActivityActions>()
    val actions = _actions.receiveAsFlow()

    private val _status = Channel<MainActivityStatus>()
    val status = _status.receiveAsFlow()

    fun viewCreated() {
        viewModelScope.launch {
            getNotesList()
        }
    }

    private fun getNotesList() {
        executeUseCase(action = {
            _status.send(MainActivityStatus.IsLoading)
            val result = getNoteListUseCase.execute(Unit)
            if (result.isEmpty()) {
                _actions.send(MainActivityActions.ShowEmptyNotes)
            } else {
                _actions.send(MainActivityActions.ShowNotes(result))
            }
        }, exceptionHandler = {
            _status.send(MainActivityStatus.Error(it.toString()))
        }, finallyHandler = {
            _status.send(MainActivityStatus.Idle)
        })
    }

    fun onNoteClicked(note: NoteModel) {
        viewModelScope.launch {
            _actions.send(MainActivityActions.GoToNoteDetail(note))
        }
    }

    fun onAddNoteClicked() {
        viewModelScope.launch {
            _actions.send(MainActivityActions.GoToAddNote)
        }
    }

    fun onSearchTextChanged(text: String) {
        executeUseCase(action = {
            _status.send(MainActivityStatus.IsLoading)
            val result = searchNotesUseCase.execute(text)
            if (result.isEmpty()) {
                _actions.send(MainActivityActions.ShowEmptyNotes)
            } else {
                _actions.send(MainActivityActions.ShowNotes(result))
            }
        }, exceptionHandler = {
            _status.send(MainActivityStatus.Error(it.toString()))
        }, finallyHandler = {
            _status.send(MainActivityStatus.Idle)
        })
    }
}
