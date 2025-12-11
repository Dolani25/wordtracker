package com.wordtracker

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wordtracker.service.ClipboardService
import com.wordtracker.ui.screens.*
import com.wordtracker.ui.theme.WordTrackerTheme
import com.wordtracker.viewmodel.WordViewModel
import com.wordtracker.viewmodel.WordViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private lateinit var prefs: SharedPreferences
    private val viewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordTrackerApplication).repository)
    }
    
    companion object {
        private const val PREFS_NAME = "WordTrackerPrefs"
        private const val KEY_AUTO_START = "auto_start_enabled"
        private const val KEY_SERVICE_ENABLED = "service_enabled"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        
        setContent {
            WordTrackerTheme {
                WordTrackerApp(viewModel)
            }
        }
        
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.serviceEnabled.collect { enabled ->
                    if (enabled) {
                        startClipboardService()
                    } else {
                        stopClipboardService()
                    }
                }
            }
        }
    }

    private fun startClipboardService() {
        val serviceIntent = Intent(this, ClipboardService::class.java).apply {
            action = ClipboardService.ACTION_START_SERVICE
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        
        prefs.edit().putBoolean(KEY_SERVICE_ENABLED, true).apply()
        Toast.makeText(this, "WordTracker service started", Toast.LENGTH_SHORT).show()
    }

    private fun stopClipboardService() {
        val serviceIntent = Intent(this, ClipboardService::class.java).apply {
            action = ClipboardService.ACTION_STOP_SERVICE
        }
        startService(serviceIntent)
        prefs.edit().putBoolean(KEY_SERVICE_ENABLED, false).apply()
        Toast.makeText(this, "WordTracker service stopped", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordTrackerApp(viewModel: WordViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = { navController.navigate("home") },
                    icon = { Icon(Icons.Default.Home, "Home") },
                    label = { Text("Home") }
                )
                
                NavigationBarItem(
                    selected = currentRoute == "words",
                    onClick = { navController.navigate("words") },
                    icon = { Icon(Icons.Default.Book, "Words") },
                    label = { Text("Words") }
                )
                
                NavigationBarItem(
                    selected = currentRoute == "favorites",
                    onClick = { navController.navigate("favorites") },
                    icon = { Icon(Icons.Default.Favorite, "Favorites") },
                    label = { Text("Favorites") }
                )
                
                NavigationBarItem(
                    selected = currentRoute == "settings",
                    onClick = { navController.navigate("settings") },
                    icon = { Icon(Icons.Default.Settings, "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(viewModel) }
            composable("words") { WordsScreen(viewModel) }
            composable("favorites") { FavoritesScreen(viewModel) }
            composable("settings") { SettingsScreen(viewModel) }
        }
    }
}