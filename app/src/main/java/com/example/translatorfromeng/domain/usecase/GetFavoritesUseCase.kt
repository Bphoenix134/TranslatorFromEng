package com.example.translatorfromeng.domain.usecase

import com.example.translatorfromeng.domain.repository.TranslationRepository

class GetFavoritesUseCase(private val repo: TranslationRepository) {
    operator fun invoke() = repo.getFavorites()
}