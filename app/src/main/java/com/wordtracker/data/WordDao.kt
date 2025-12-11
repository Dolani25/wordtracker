package com.wordtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    
    @Query("SELECT * FROM words ORDER BY dateAdded DESC")
    fun getAllWords(): Flow<List<Word>>
    
    @Query("SELECT * FROM words WHERE word LIKE :query OR definition LIKE :query ORDER BY dateAdded DESC")
    fun searchWords(query: String): Flow<List<Word>>
    
    @Query("SELECT * FROM words WHERE isFavorite = 1 ORDER BY dateAdded DESC")
    fun getFavoriteWords(): Flow<List<Word>>
    
    @Query("SELECT * FROM words ORDER BY dateAdded DESC LIMIT 50")
    fun getRecentWords(): Flow<List<Word>>
    
    @Query("SELECT * FROM words WHERE word = :word LIMIT 1")
    suspend fun getWord(word: String): Word?
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word): Long
    
    @Update
    suspend fun updateWord(word: Word)
    
    @Delete
    suspend fun deleteWord(word: Word)
    
    @Query("DELETE FROM words")
    suspend fun deleteAllWords()
    
    @Query("SELECT COUNT(*) FROM words")
    fun getWordCount(): LiveData<Int>
    
    @Query("UPDATE words SET usageCount = usageCount + 1 WHERE id = :wordId")
    suspend fun incrementUsageCount(wordId: Long)
    
    @Query("UPDATE words SET isFavorite = :isFavorite WHERE id = :wordId")
    suspend fun setFavorite(wordId: Long, isFavorite: Boolean)
}