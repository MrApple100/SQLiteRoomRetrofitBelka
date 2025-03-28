package ru.mrapple100.sqlroom.local

import androidx.room.Embedded
import androidx.room.Relation

data class BelkaWithPhrase(
    @Embedded
    val belka: Belka,

    @Relation(
        entityColumn = "phraseId",
        parentColumn = "phraseId"
    )
    val phrase: Phrase
)








