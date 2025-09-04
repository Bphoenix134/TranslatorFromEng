package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetTranslationUseCaseTest {

    private lateinit var repo: TranslationRepository
    private lateinit var useCase: GetTranslationUseCase

    @Before
    fun setUp() {
        repo = mock()
        useCase = GetTranslationUseCase(repo)
    }

    @Test
    fun `success - returns translation from repository`() = runTest {
        // Arrange
        val word = "hello"
        val expectedTranslation = Translation(
            englishWord = word,
            russianTranslation = "привет",
            isFavorite = false,
            timestamp = System.currentTimeMillis()
        )
        whenever(repo.getTranslation(word)).thenReturn(Result.success(expectedTranslation))

        // Act
        val result = useCase(word)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expectedTranslation, result.getOrNull())
    }

    @Test
    fun `failure - returns error from repository`() = runTest {
        // Arrange
        val word = "hello"
        val exception = Exception("No translation found")
        whenever(repo.getTranslation(word)).thenReturn(Result.failure(exception))

        // Act
        val result = useCase(word)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}