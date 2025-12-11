package com.wordtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String,
    val definition: String? = null,
    val pronunciation: String? = null,
    val partOfSpeech: String? = null,
    val source: String? = null,
    val copiedText: String? = null,
    val dateAdded: Date = Date(),
    val isFavorite: Boolean = false,
    val usageCount: Int = 1
) {
    override fun toString(): String {
        return "Word(word='$word', definition=$definition, dateAdded=$dateAdded)"
    }
}