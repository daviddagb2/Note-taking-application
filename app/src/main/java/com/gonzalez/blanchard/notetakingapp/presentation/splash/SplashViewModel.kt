package com.gonzalez.blanchard.notetakingapp.presentation.splash

import androidx.lifecycle.viewModelScope
import com.gonzalez.blanchard.notetakingapp.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    private val _actions = Channel<SplashActions>()
    val action = _actions.receiveAsFlow()

    fun viewCreated() {
        viewModelScope.launch {
            _actions.send(SplashActions.GoToMain)
        }
    }
}
