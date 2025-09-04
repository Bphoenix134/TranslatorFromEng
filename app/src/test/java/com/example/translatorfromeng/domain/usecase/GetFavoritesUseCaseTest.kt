package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetFavoritesUseCaseTest {

    private lateinit var repo: TranslationRepository
    private lateinit var useCase: GetFavoritesUseCase

    @Before
    fun setUp() {
        repo = mock()
        useCase = GetFavoritesUseCase(repo)
    }

    @Test
    fun `getFavorites - returns flow of favorite translations from repository`() = runTest {
        // Arrange
        val favorites = listOf(
            Translation(
                id = 1,
                englishWord = "hello",
                russianTranslation = "привет",
                isFavorite = true,
                timestamp = System.currentTimeMillis()
            ),
            Translation(
                id = 2,
                englishWord = "world",
                russianTranslation = "мир",
                isFavorite = true,
                timestamp = System.currentTimeMillis()
            )
        )
        whenever(repo.getFavorites()).thenReturn(flowOf(favorites))

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(favorites, result)
        assertTrue(result.all { it.isFavorite }) // Проверяем, что все переводы избранные
    }

    @Test
    fun `getFavorites - returns empty flow when no favorites`() = runTest {
        // Arrange
        whenever(repo.getFavorites()).thenReturn(flowOf(emptyList()))

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(emptyList<Translation>(), result)
    }
}