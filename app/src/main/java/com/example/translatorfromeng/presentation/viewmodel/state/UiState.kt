package com.example.translatorfromeng.presentation.viewmodel.state

import com.example.translatorfromeng.domain.model.Translation

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val translation: Translation) : UiState()
    data class Error(val message: String) : UiState()
}