package com.example.translatorfromeng.data.repository

import com.example.translatorfromeng.data.local.TranslationDao
import com.example.translatorfromeng.data.local.model.TranslationEntity
import com.example.translatorfromeng.data.mapper.toDomain
import com.example.translatorfromeng.data.mapper.toEntity
import com.example.translatorfromeng.data.remote.SkyengApi
import com.example.translatorfromeng.data.remote.dto.Meaning
import com.example.translatorfromeng.data.remote.dto.TranslationResponse
import com.example.translatorfromeng.data.remote.dto.WordResponse
import com.example.translatorfromeng.domain.model.Translation
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TranslationRepositoryImplTest {

    private lateinit var dao: TranslationDao
    private lateinit var api: SkyengApi
    private lateinit var repo: TranslationRepositoryImpl

    @Before
    fun setUp() {
        dao = mock()
        api = mock()
        repo = TranslationRepositoryImpl(api, dao)
    }

    @Test
    fun `getTranslation - returns from cache`() = runTest {
        // Arrange
        val word = "hello"
        val entity = TranslationEntity(
            id = 1,
            englishWord = word,
            russianTranslation = "привет",
            isFavorite = false,
            timestamp = 123456
        )
        whenever(dao.getByWord(word)).thenReturn(entity)

        // Act
        val result = repo.getTranslation(word)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(entity.toDomain(), result.getOrNull())
    }

    @Test
    fun `getTranslation - fetches from API when cache is empty`() = runTest {
        // Arrange
        val word = "hello"
        whenever(dao.getByWord(word)).thenReturn(null)
        whenever(api.searchWord(word)).thenReturn(
            listOf(
                WordResponse(
                    text = word,
                    meanings = listOf(Meaning(TranslationResponse("привет")))
                )
            )
        )

        // Act
        val result = repo.getTranslation(word)

        // Assert
        assertTrue(result.isSuccess)
        val translation = result.getOrNull()
        assertEquals(word, translation?.englishWord)
        assertEquals("привет", translation?.russianTranslation)
    }

    @Test
    fun `getTranslation - returns error when API fails`() = runTest {
        // Arrange
        val word = "hello"
        whenever(dao.getByWord(word)).thenReturn(null)
        whenever(api.searchWord(word)).thenThrow(RuntimeException("Network error"))

        // Act
        val result = repo.getTranslation(word)

        // Assert
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `insertTranslation - inserts into DAO`() = runTest {
        // Arrange
        val translation = Translation(
            englishWord = "hello",
            russianTranslation = "привет"
        )

        // Act
        repo.insertTranslation(translation)

        // Assert
        verify(dao).insert(translation.toEntity())
    }
}