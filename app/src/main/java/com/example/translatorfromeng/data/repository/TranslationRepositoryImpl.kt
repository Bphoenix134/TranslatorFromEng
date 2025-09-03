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

    override suspend fun getTranslation(word: String): Translation {
        val response = api.searchWord(word)
        if (response.isNotEmpty()) {
            val meaning = response[0].meanings[0]
            return Translation(
                englishWord = word,
                russianTranslation = meaning.translation.text
            )
        }
        throw Exception("No translation found")
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