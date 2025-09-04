package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class InsertTranslationUseCaseTest {

    private lateinit var repo: TranslationRepository
    private lateinit var useCase: InsertTranslationUseCase

    @Before
    fun setUp() {
        repo = mock()
        useCase = InsertTranslationUseCase(repo)
    }

    @Test
    fun `insert - calls repository insertTranslation`() = runTest {
        // Arrange
        val translation = Translation(
            englishWord = "hello",
            russianTranslation = "привет",
            isFavorite = false,
            timestamp = System.currentTimeMillis()
        )

        // Act
        useCase(translation)

        // Assert
        verify(repo).insertTranslation(translation)
    }
}