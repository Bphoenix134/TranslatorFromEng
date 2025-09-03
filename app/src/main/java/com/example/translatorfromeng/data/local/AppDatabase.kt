package com.example.translatorfromeng.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.translatorfromeng.data.local.model.TranslationEntity

@Database(entities = [TranslationEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun translationDao(): TranslationDao
}