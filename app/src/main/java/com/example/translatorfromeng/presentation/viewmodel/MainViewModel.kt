package com.example.translatorfromeng.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.usecase.DeleteTranslationUseCase
import com.example.translatorfromeng.domain.usecase.GetHistoryUseCase
import com.example.translatorfromeng.domain.usecase.GetTranslationUseCase
import com.example.translatorfromeng.domain.usecase.InsertTranslationUseCase
import com.example.translatorfromeng.domain.usecase.ToggleFavoriteUseCase
import com.example.translatorfromeng.presentation.viewmodel.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTranslationUseCase: GetTranslationUseCase,
    private val insertUseCase: InsertTranslationUseCase,
    private val deleteUseCase: DeleteTranslationUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    val history: StateFlow<List<Translation>> = getHistoryUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun translate(word: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val translation = getTranslationUseCase(word)
                insertUseCase(translation)
                _uiState.value = UiState.Success(translation)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error")
            }
        }
    }

    fun delete(translation: Translation) {
        viewModelScope.launch { deleteUseCase(translation) }
    }

    fun toggleFavorite(translation: Translation) {
        viewModelScope.launch { toggleFavoriteUseCase(translation) }
    }
}