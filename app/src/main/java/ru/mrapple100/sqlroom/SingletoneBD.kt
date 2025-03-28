package ru.mrapple100.sqlroom

import androidx.room.Room
import ru.mrapple100.sqlroom.local.AppDatabase

object SingletoneBD {


    val db = Room.databaseBuilder(
        MainActivity.getContext(),
        AppDatabase::class.java, "belka4"
    ).build()

}