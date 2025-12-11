package com.wordtracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wordtracker.data.Word
import com.wordtracker.viewmodel.WordViewModel

@Composable
fun FavoritesScreen(viewModel: WordViewModel) {
    val favoriteWords by viewModel.favoriteWords.collectAsState(initial = emptyList())
    val selectedWord by viewModel.selectedWord.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Favorite Words",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (favoriteWords.isEmpty()) {
            EmptyState(message = "No favorite words yet")
        } else {
            LazyColumn {
                items(favoriteWords) { word ->
                    FavoriteWordItem(
                        word = word,
                        onClick = { viewModel.selectWord(word) },
                        onToggleFavorite = { viewModel.toggleFavorite(word) }
                    )
                }
            }
        }
    }
    
    selectedWord?.let { word ->
        WordDetailDialog(
            word = word,
            onDismiss = { viewModel.clearSelectedWord() },
            onToggleFavorite = { viewModel.toggleFavorite(word) },
            onDelete = { 
                viewModel.deleteWord(word)
                viewModel.clearSelectedWord()
            }
        )
    }
}

@Composable
fun FavoriteWordItem(
    word: Word,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp)
                    )
                    
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                word.definition?.let { definition ->
                    Text(
                        text = definition,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp),
                        maxLines = 2
                    )
                }
                
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    word.partOfSpeech?.let { pos ->
                        Text(
                            text = pos,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    if (word.usageCount > 1) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Used ${word.usageCount} times",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Remove from favorites",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}