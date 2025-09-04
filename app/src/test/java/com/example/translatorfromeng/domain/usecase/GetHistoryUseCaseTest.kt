package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetHistoryUseCaseTest {

    private lateinit var repo: TranslationRepository
    private lateinit var useCase: GetHistoryUseCase

    @Before
    fun setUp() {
        repo = mock()
        useCase = GetHistoryUseCase(repo)
    }

    @Test
    fun `getHistory - returns flow of translations from repository`() = runTest {
        // Arrange
        val translations = listOf(
            Translation(
                id = 1,
                englishWord = "hello",
                russianTranslation = "привет",
                isFavorite = false,
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
        whenever(repo.getHistory()).thenReturn(flowOf(translations))

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(translations, result)
    }

    @Test
    fun `getHistory - returns empty flow when repository returns empty`() = runTest {
        // Arrange
        whenever(repo.getHistory()).thenReturn(flowOf(emptyList()))

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(emptyList<Translation>(), result)
    }
}