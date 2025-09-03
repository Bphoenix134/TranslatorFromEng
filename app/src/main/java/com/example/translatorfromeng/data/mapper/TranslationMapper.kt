package com.example.translatorfromeng.data.mapper

import com.example.translatorfromeng.data.local.model.TranslationEntity
import com.example.translatorfromeng.domain.model.Translation

fun Translation.toEntity(): TranslationEntity {
    return TranslationEntity(
        id,
        englishWord,
        russianTranslation,
        isFavorite,
        timestamp
    )
}

fun TranslationEntity.toDomain() : Translation {
    return Translation(
        id,
        englishWord,
        russianTranslation,
        isFavorite,
        timestamp
    )
}