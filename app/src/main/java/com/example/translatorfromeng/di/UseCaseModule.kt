package com.example.translatorfromeng.di

import com.example.translatorfromeng.domain.repository.TranslationRepository
import com.example.translatorfromeng.domain.usecase.DeleteTranslationUseCase
import com.example.translatorfromeng.domain.usecase.GetFavoritesUseCase
import com.example.translatorfromeng.domain.usecase.GetHistoryUseCase
import com.example.translatorfromeng.domain.usecase.GetTranslationUseCase
import com.example.translatorfromeng.domain.usecase.InsertTranslationUseCase
import com.example.translatorfromeng.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTranslationUseCase(repo: TranslationRepository): GetTranslationUseCase {
        return GetTranslationUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideInsertTranslationYseCase(repo: TranslationRepository): InsertTranslationUseCase {
        return InsertTranslationUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideDeleteTranslationUseCase(repo: TranslationRepository): DeleteTranslationUseCase {
        return DeleteTranslationUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(repo: TranslationRepository): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetHistoryUseCase(repo: TranslationRepository): GetHistoryUseCase {
        return GetHistoryUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetFavoritesUseCase(repo: TranslationRepository): GetFavoritesUseCase {
        return GetFavoritesUseCase(repo)
    }
}