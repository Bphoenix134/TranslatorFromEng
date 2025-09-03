package com.example.translatorfromeng.di

import com.example.translatorfromeng.data.local.TranslationDao
import com.example.translatorfromeng.data.remote.SkyengApi
import com.example.translatorfromeng.data.repository.TranslationRepositoryImpl
import com.example.translatorfromeng.domain.repository.TranslationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTranslationRepository(api: SkyengApi, dao: TranslationDao): TranslationRepository {
        return TranslationRepositoryImpl(api, dao)
    }
}