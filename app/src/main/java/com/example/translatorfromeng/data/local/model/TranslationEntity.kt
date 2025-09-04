package com.example.translatorfromeng.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "translation", indices = [Index(value = ["englishWord"], unique = true)])
data class TranslationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val englishWord: String,
    val russianTranslation: String,
    val isFavorite: Boolean,
    val timestamp: Long
)
