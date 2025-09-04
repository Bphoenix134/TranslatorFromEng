package com.example.translatorfromeng.data.repository

import com.example.translatorfromeng.data.local.TranslationDao
import com.example.translatorfromeng.data.mapper.toDomain
import com.example.translatorfromeng.data.mapper.toEntity
import com.example.translatorfromeng.data.remote.SkyengApi
import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TranslationRepositoryImpl (
    private val api: SkyengApi,
    private val dao: TranslationDao
) : TranslationRepository {

    override suspend fun getTranslation(word: String): Result<Translation> {
        val local = dao.getByWord(word)
        if (local != null) {
            return Result.success(local.toDomain())
        }
        return try {
            val response = api.searchWord(word)
            val text = response.firstOrNull()?.meanings?.firstOrNull()?.translation?.text
            if (text != null) {
                Result.success(
                    Translation(
                        englishWord = word,
                        russianTranslation = text,
                        isFavorite = false,
                        timestamp = System.currentTimeMillis()
                    )
                )
            } else {
                Result.failure(Exception("No translation found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertTranslation(translation: Translation) {
        dao.insert(translation.toEntity())
    }

    override suspend fun deleteTranslation(translation: Translation) {
        dao.delete(translation.toEntity())
    }

    override suspend fun toggleFavorite(translation: Translation) {
        dao.updateFavorite(translation.id, !translation.isFavorite)
    }

    override fun getHistory(): Flow<List<Translation>> {
        return dao.getAll().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }

    override fun getFavorites(): Flow<List<Translation>> {
        return dao.getFavorites().map { entities ->
            entities.map { entity -> entity.toDomain() }
        }
    }
}