package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.model.Translation
import com.example.translatorfromeng.domain.repository.TranslationRepository

class GetTranslationUseCase(private val repo: TranslationRepository) {
    suspend operator fun invoke(word: String): Translation = repo.getTranslation(word)
}