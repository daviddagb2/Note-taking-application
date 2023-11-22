package com.gonzalez.blanchard.notetakingapp.presentation.main

sealed class MainActivityStatus {
    class Error(val error: String) : MainActivityStatus()
    object IsLoading : MainActivityStatus()
    object Idle : MainActivityStatus()
}
