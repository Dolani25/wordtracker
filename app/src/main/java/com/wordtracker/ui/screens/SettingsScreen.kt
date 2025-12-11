package com.wordtracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wordtracker.viewmodel.WordViewModel

@Composable
fun SettingsScreen(viewModel: WordViewModel) {
    val context = LocalContext.current
    val serviceEnabled by viewModel.serviceEnabled.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        SettingCard(
            title = "Service Control",
            description = "Toggle clipboard monitoring service"
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Background Service",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = if (serviceEnabled) "Running" else "Stopped",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Switch(
                    checked = serviceEnabled,
                    onCheckedChange = { viewModel.toggleService() }
                )
            }
        }
        
        SettingCard(
            title = "Data Management",
            description = "Manage your word collection"
        ) {
            Column {
                SettingItem(
                    icon = Icons.Default.Delete,
                    title = "Clear All Words",
                    description = "Remove all collected words",
                    onClick = {
                        viewModel.deleteAllWords()
                    }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingItem(
                    icon = Icons.Default.Share,
                    title = "Export Words",
                    description = "Export word collection to file",
                    onClick = {
                        // TODO: Implement export functionality
                    }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingItem(
                    icon = Icons.Default.Add,
                    title = "Import Words",
                    description = "Import words from file",
                    onClick = {
                        // TODO: Implement import functionality
                    }
                )
            }
        }
        
        SettingCard(
            title = "Preferences",
            description = "Customize app behavior"
        ) {
            Column {
                SettingItem(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    description = "Configure notification settings",
                    onClick = {
                        // TODO: Open notification settings
                    }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingItem(
                    icon = Icons.Default.Language,
                    title = "Definition Source",
                    description = "Choose dictionary API",
                    onClick = {
                        // TODO: Show API selection dialog
                    }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingItem(
                    icon = Icons.Default.Storage,
                    title = "Storage Usage",
                    description = "View storage statistics",
                    onClick = {
                        // TODO: Show storage info
                    }
                )
            }
        }
        
        SettingCard(
            title = "About",
            description = "App information and help"
        ) {
            Column {
                SettingItem(
                    icon = Icons.Default.Info,
                    title = "About WordTracker",
                    description = "Version 1.0.0",
                    onClick = {
                        // TODO: Show about dialog
                    }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingItem(
                    icon = Icons.Default.Help,
                    title = "Help & FAQ",
                    description = "Get help using the app",
                    onClick = {
                        // TODO: Open help
                    }
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                SettingItem(
                    icon = Icons.Default.Email,
                    title = "Contact Support",
                    description = "Get help or report issues",
                    onClick = {
                        // TODO: Open contact form
                    }
                )
            }
        }
    }
}

@Composable
fun SettingCard(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            content()
        }
    }
}

@Composable
fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.padding(end = 16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Navigate",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}