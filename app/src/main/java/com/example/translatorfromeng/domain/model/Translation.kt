package com.example.translatorfromeng.domain.model

data class Translation(
    val id: Long = 0,
    val englishWord: String,
    val russianTranslation: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)