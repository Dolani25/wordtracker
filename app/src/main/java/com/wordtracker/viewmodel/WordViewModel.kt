package com.wordtracker.viewmodel

import androidx.lifecycle.*
import com.wordtracker.data.Word
import com.wordtracker.repository.WordRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _serviceEnabled = MutableStateFlow(false)
    val serviceEnabled: StateFlow<Boolean> = _serviceEnabled.asStateFlow()
    
    private val _selectedWord = MutableStateFlow<Word?>(null)
    val selectedWord: StateFlow<Word?> = _selectedWord.asStateFlow()
    
    val allWords = repository.getAllWords()
    val favoriteWords = repository.getFavoriteWords()
    val recentWords = repository.getRecentWords()
    val wordCount = repository.getWordCount()
    
    val filteredWords = combine(
        allWords,
        _searchQuery
    ) { words, query ->
        if (query.isBlank()) {
            words
        } else {
            words.filter { 
                it.word.contains(query, ignoreCase = true) ||
                it.definition?.contains(query, ignoreCase = true) == true
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun setServiceEnabled(enabled: Boolean) {
        _serviceEnabled.value = enabled
    }
    
    fun toggleService() {
        _serviceEnabled.value = !_serviceEnabled.value
    }
    
    fun selectWord(word: Word) {
        _selectedWord.value = word
    }
    
    fun clearSelectedWord() {
        _selectedWord.value = null
    }
    
    fun toggleFavorite(word: Word) {
        viewModelScope.launch {
            repository.toggleFavorite(word.id, !word.isFavorite)
        }
    }
    
    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.deleteWord(word)
        }
    }
    
    fun deleteAllWords() {
        viewModelScope.launch {
            repository.deleteAllWords()
        }
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}