package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository

class DeleteTranslationUseCase(private val repo: TranslationRepository) {
    suspend operator fun invoke(translation: Translation) = repo.deleteTranslation(translation)
}