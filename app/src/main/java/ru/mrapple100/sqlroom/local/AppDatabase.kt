package ru.mrapple100.sqlroom.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database( entities = arrayOf(Belka::class, Phrase::class), version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun belkaDao(): BelkaDAO
}