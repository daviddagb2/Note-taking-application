package com.gonzalez.blanchard.notetakingapp.presentation.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gonzalez.blanchard.notetakingapp.core.BaseViewModel
import com.gonzalez.blanchard.notetakingapp.domain.models.NoteModel
import com.gonzalez.blanchard.notetakingapp.domain.usecases.DeleteNoteUseCase
import com.gonzalez.blanchard.notetakingapp.domain.usecases.GetNoteUseCase
import com.gonzalez.blanchard.notetakingapp.domain.usecases.InsertNoteUseCase
import com.gonzalez.blanchard.notetakingapp.domain.usecases.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : BaseViewModel() {

    private val _actions = Channel<NoteActions>()
    val actions = _actions.receiveAsFlow()

    private val _status = Channel<NoteStatus>()
    val status = _status.receiveAsFlow()

    var _noteItem = MutableLiveData<NoteModel>()
    val noteItem: MutableLiveData<NoteModel> = _noteItem

    var shouldSaveOnBack: Boolean = true
        private set

    fun viewCreated() {
        viewModelScope.launch {
            _noteItem.value = NoteModel(
                id = 0,
                title = "",
                content = "",
                date = "",
                image = "",
                isFavourite = false,
                isArchived = false,
                color = "",
                reminder = "",
                timestamp = 0L,
            )
            setTimestamp()
            setDate()
        }
    }

    fun getNoteDetail(noteId: String) {
        executeUseCase(action = {
            _status.send(NoteStatus.IsLoading)
            val result = getNoteUseCase.execute(noteId.toLong())
            _noteItem.value = result
            _actions.send(NoteActions.LoadNote(result))
        }, exceptionHandler = {
            _status.send(NoteStatus.Error(it.toString()))
        }, finallyHandler = {
            _status.send(NoteStatus.Idle)
        })
    }

    fun setShouldSaveOnBack(value: Boolean) {
        shouldSaveOnBack = value
    }

    fun setTitle(title: String) {
        _noteItem.value = _noteItem.value?.copy(title = title)
    }

    fun setContent(content: String) {
        _noteItem.value = _noteItem.value?.copy(content = content)
    }

    fun setId(id: Long) {
        _noteItem.value = _noteItem.value?.copy(id = id)
    }

    private fun setDate() {
        viewModelScope.launch {
            val currentDateTime = DateTime()
            val creationDate = currentDateTime.toDate()
            _noteItem.value = _noteItem.value?.copy(date = creationDate.toString())
            _actions.send(NoteActions.SetDate(creationDate.toString()))
        }
    }

    private fun setTimestamp() {
        val currentTimeMillis = System.currentTimeMillis()
        val timestamp = (currentTimeMillis / 1000)
        _noteItem.value = _noteItem.value?.copy(timestamp = timestamp)
    }

    fun onSaveNote() {
        executeUseCase(action = {
            _status.send(NoteStatus.IsLoading)
            setDate()
            setTimestamp()
            if (_noteItem.value?.id != 0L) {
                _noteItem.value?.let { updateNoteUseCase.execute(it) }
            } else {
                _noteItem.value?.let {
                    insertNoteUseCase.execute(it)
                }
            }
            _status.send(NoteStatus.Idle)
        }, exceptionHandler = {
            _status.send(NoteStatus.Error(it.toString()))
        }, finallyHandler = {
            _status.send(NoteStatus.Idle)
        })
    }

    fun onDeleteNote() {
        val noteToDelete = noteItem.value
        if (noteToDelete != null && noteToDelete.id != 0L) {
            executeUseCase(action = {
                _status.send(NoteStatus.IsLoading)
                deleteNoteUseCase.execute(noteToDelete)
            }, exceptionHandler = {
                _status.send(NoteStatus.Error(it.toString()))
            }, finallyHandler = {
                _status.send(NoteStatus.Idle)
            })
        }
        setShouldSaveOnBack(false)
    }
}
