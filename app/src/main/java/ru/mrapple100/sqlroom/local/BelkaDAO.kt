package ru.mrapple100.sqlroom.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ru.mrapple100.sqlroom.local.Belka


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


    @Insert
    fun insertPhrase(phrase: Phrase):Unit

    @Query("SELECT * FROM Phrase")
    fun getAllPhrase():List<Phrase>

    @Transaction
    @Query("SELECT * FROM Belka")
    fun getBelkaWithPhrase():List<BelkaWithPhrase>
}