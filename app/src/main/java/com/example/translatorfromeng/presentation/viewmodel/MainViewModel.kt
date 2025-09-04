package com.example.translatorfromeng.presentation.viewmodel

import android.database.sqlite.SQLiteConstraintException
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
import kotlinx.coroutines.delay
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
    getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    val history: StateFlow<List<Translation>> = getHistoryUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun translate(word: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getTranslationUseCase(word)
                .onSuccess {
                    try {
                        insertUseCase(it)
                        _uiState.value = UiState.Success(it)
                        delay(5000)
                        _uiState.value = UiState.Idle
                    } catch (e: SQLiteConstraintException) {
                        _uiState.value = UiState.Success(it)
                    }
                }
                .onFailure {
                    _uiState.value = UiState.Error(it.message ?: "Unknown error")
                    delay(5000)
                    _uiState.value = UiState.Idle
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