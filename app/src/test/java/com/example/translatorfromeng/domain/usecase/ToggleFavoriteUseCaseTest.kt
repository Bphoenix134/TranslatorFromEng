package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleFavoriteUseCaseTest {

    private lateinit var repo: TranslationRepository
    private lateinit var useCase: ToggleFavoriteUseCase

    @Before
    fun setUp() {
        repo = mock()
        useCase = ToggleFavoriteUseCase(repo)
    }

    @Test
    fun `toggleFavorite - calls repository toggleFavorite`() = runTest {
        // Arrange
        val translation = Translation(
            id = 1,
            englishWord = "hello",
            russianTranslation = "привет",
            isFavorite = false,
            timestamp = System.currentTimeMillis()
        )

        // Act
        useCase(translation)

        // Assert
        verify(repo).toggleFavorite(translation)
    }
}