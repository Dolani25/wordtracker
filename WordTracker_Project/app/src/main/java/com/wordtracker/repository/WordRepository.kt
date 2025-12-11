package com.wordtracker.repository

import android.util.Log
import com.wordtracker.api.DictionaryApi
import com.wordtracker.api.RetrofitClient
import com.wordtracker.data.Word
import com.wordtracker.data.WordDao
import kotlinx.coroutines.*
import java.util.*

class WordRepository(
    private val wordDao: WordDao,
    private val externalScope: CoroutineScope
) {
    private val dictionaryApi: DictionaryApi = RetrofitClient.dictionaryApi
    
    companion object {
        private const val TAG = "WordRepository"
    }

    fun getAllWords() = wordDao.getAllWords()
    
    fun searchWords(query: String) = wordDao.searchWords("%$query%")
    
    fun getFavoriteWords() = wordDao.getFavoriteWords()
    
    fun getRecentWords() = wordDao.getRecentWords()
    
    fun getWordCount() = wordDao.getWordCount()

    suspend fun addWordFromClipboard(copiedText: String, source: String? = null) {
        val words = extractWords(copiedText)
        
        words.forEach { wordText ->
            try {
                val existingWord = wordDao.getWord(wordText.lowercase())
                
                if (existingWord != null) {
                    wordDao.incrementUsageCount(existingWord.id)
                    Log.d(TAG, "Incremented usage count for: $wordText")
                } else {
                    val definition = fetchWordDefinition(wordText)
                    val word = Word(
                        word = wordText.lowercase(),
                        definition = definition?.definition,
                        pronunciation = definition?.pronunciation,
                        partOfSpeech = definition?.partOfSpeech,
                        source = source,
                        copiedText = copiedText,
                        dateAdded = Date()
                    )
                    
                    wordDao.insertWord(word)
                    Log.d(TAG, "Added new word: $wordText")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing word: $wordText", e)
            }
        }
    }

    private fun extractWords(text: String): List<String> {
        return text.split(Regex("[^a-zA-Z']+"))
            .filter { it.isNotBlank() && it.length > 1 }
            .map { it.trim() }
            .distinct()
    }

    private suspend fun fetchWordDefinition(word: String): WordDefinition? {
        return withContext(Dispatchers.IO) {
            try {
                val response = dictionaryApi.getWordDefinition(word.lowercase())
                if (response.isSuccessful && response.body() != null) {
                    val dictionaryResponse = response.body()!!.firstOrNull()
                    dictionaryResponse?.let { dictResp ->
                        val meaning = dictResp.meanings?.firstOrNull()
                        val definition = meaning?.definitions?.firstOrNull()
                        
                        WordDefinition(
                            definition = definition?.definition,
                            pronunciation = dictResp.phonetic,
                            partOfSpeech = meaning?.partOfSpeech
                        )
                    }
                } else {
                    Log.w(TAG, "Failed to fetch definition for: $word, response: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching definition for: $word", e)
                null
            }
        }
    }

    suspend fun toggleFavorite(wordId: Long, isFavorite: Boolean) {
        wordDao.setFavorite(wordId, isFavorite)
    }

    suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }

    suspend fun deleteAllWords() {
        wordDao.deleteAllWords()
    }

    private data class WordDefinition(
        val definition: String?,
        val pronunciation: String?,
        val partOfSpeech: String?
    )
}