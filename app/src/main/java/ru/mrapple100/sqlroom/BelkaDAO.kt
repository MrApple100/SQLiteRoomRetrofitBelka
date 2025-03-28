package ru.mrapple100.sqlroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface BelkaDAO {
    @Query("SELECT * FROM Belka ")
    fun getAllBelka(): List<Belka>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBelka(belka: Belka):Unit

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateBelka(belka: Belka):Unit

    @Delete
    fun deleteBelka(belka: Belka):Unit
}