package ru.mrapple100.sqlroom

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

object SingletoneBD {


    val db = Room.databaseBuilder(
        MainActivity.getContext(),
        AppDatabase::class.java, "belka1"
    ).build()

}