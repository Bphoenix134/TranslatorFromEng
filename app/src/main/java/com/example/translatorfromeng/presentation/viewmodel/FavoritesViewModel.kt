package com.example.translatorfromeng.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.usecase.DeleteTranslationUseCase
import com.example.translatorfromeng.domain.usecase.GetFavoritesUseCase
import com.example.translatorfromeng.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoritesUseCase: GetFavoritesUseCase,
    private val deleteUseCase: DeleteTranslationUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val favorites: StateFlow<List<Translation>> = getFavoritesUseCase().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun delete(translation: Translation) {
        viewModelScope.launch { deleteUseCase(translation) }
    }

    fun toggleFavorite(translation: Translation) {
        viewModelScope.launch { toggleFavoriteUseCase(translation) }
    }
}