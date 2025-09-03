package com.example.translatorfromeng.domain.repository

import com.example.translatorfromeng.domain.model.Translation
import kotlinx.coroutines.flow.Flow

interface TranslationRepository {
    suspend fun getTranslation(word: String): Result<Translation>
    suspend fun insertTranslation(translation: Translation)
    suspend fun deleteTranslation(translation: Translation)
    suspend fun toggleFavorite(translation: Translation)
    fun getHistory(): Flow<List<Translation>>
    fun getFavorites(): Flow<List<Translation>>
}