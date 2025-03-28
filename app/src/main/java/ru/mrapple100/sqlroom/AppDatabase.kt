package ru.mrapple100.sqlroom

import androidx.room.Database
import androidx.room.RoomDatabase


@Database( entities = arrayOf(Belka::class), version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun belkaDao(): BelkaDAO
}