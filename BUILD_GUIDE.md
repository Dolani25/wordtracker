# WordTracker - Build and Installation Guide

## Overview

WordTracker is a sophisticated Android application that monitors your clipboard for copied words, automatically fetches their definitions, and stores them in a local database for easy reference and vocabulary building.

## Features Implemented

### ✅ Core Functionality
- **Background Clipboard Monitoring**: Continuously monitors clipboard changes
- **Word Detection**: Automatically extracts individual words from copied text
- **Definition Lookup**: Fetches word meanings using the Free Dictionary API
- **Persistent Storage**: All words stored locally with unlimited capacity
- **Smart Selection**: Intelligent word boundary detection

### ✅ User Interface
- **Modern Material Design**: Clean, intuitive interface with Compose
- **Four Main Screens**: Home, Words, Favorites, Settings
- **Real-time Updates**: Live data updates across all screens
- **Search & Filter**: Find specific words or browse by categories
- **Word Details**: Comprehensive word information with pronunciation

### ✅ Background Service
- **Toggle Control**: Easy on/off switching for monitoring
- **Notification System**: Persistent notification with quick actions
- **Battery Optimized**: Efficient background processing
- **Auto-Start Option**: Can start on device boot
- **Memory Efficient**: Minimal resource usage

## Architecture

### Components
- **MainActivity**: Entry point with Compose UI
- **ClipboardService**: Background service for clipboard monitoring
- **WordRepository**: Repository pattern for data management
- **WordDatabase**: Room database for local storage
- **DictionaryApi**: Retrofit client for definition lookup
- **WordViewModel**: MVVM ViewModel for UI state management

### Libraries Used
- AndroidX Compose for UI
- Room Database for storage
- Retrofit for API calls
- Coroutines for async operations
- Material Design components

## Building the App

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 34
- Kotlin 1.9.22
- Gradle 8.2

### Build Steps

1. **Open Project in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Choose the `WordTracker` directory

2. **Sync Project**
   - Android Studio will automatically sync the project
   - Wait for all dependencies to download

3. **Build APK**
   - Go to `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - Wait for the build to complete
   - Find the APK at: `app/build/outputs/apk/debug/app-debug.apk`

4. **Install on Device**
   - Enable "Unknown Sources" on your Android device
   - Transfer the APK to your device
   - Install the APK
   - Open the app and grant necessary permissions

## Development Setup

### Running from Android Studio
1. Connect an Android device or start an emulator
2. Click "Run" button in Android Studio
3. Select your device/emulator
4. The app will install and launch automatically

### Command Line Build
```bash
# Navigate to project directory
cd WordTracker

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

## Configuration

### Permissions Required
- `INTERNET`: For definition lookup
- `RECEIVE_BOOT_COMPLETED`: For auto-start functionality
- `FOREGROUND_SERVICE`: For background operation
- `VIBRATE`: For feedback (optional)

### API Configuration
The app uses the Free Dictionary API (https://dictionaryapi.dev/):
- No API key required
- Free tier with rate limits
- Supports English words only

## Usage Guide

### Getting Started
1. **Enable Service**: Toggle the background service on the Home screen
2. **Start Reading**: Open any app (browser, e-reader, etc.)
3. **Copy Words**: Long-press and copy any word or text
4. **Auto Collection**: Words are automatically detected and stored
5. **View Definitions**: Check meanings in the Words tab

### Managing Words
- **Search**: Use the search bar to find specific words
- **Favorites**: Tap the heart icon to mark words as favorites
- **Details**: Tap any word to see full definition and details
- **Delete**: Remove words from the details dialog

### Settings
- **Service Control**: Toggle background monitoring
- **Data Management**: Clear all words or export collection
- **Preferences**: Customize app behavior
- **About**: App information and help

## Troubleshooting

### Common Issues

**Service not starting:**
- Check if notification permissions are granted
- Ensure battery optimization is disabled for the app
- Restart the app and toggle the service

**No definitions found:**
- Check internet connection
- Ensure the word is in English
- Try again later (API might be rate-limited)

**Words not being collected:**
- Verify the service is enabled
- Check clipboard permissions
- Restart the service

### Performance Tips
- The app is optimized for battery life
- Background service uses minimal resources
- Database queries are efficient with Room
- Images and icons are optimized

## Future Enhancements

Potential features for future versions:
- Multiple language support
- Offline dictionary database
- Word pronunciation audio
- Study mode with flashcards
- Export to Anki or other apps
- Cloud synchronization
- Advanced filtering and categories
- Usage statistics and insights

## Technical Details

### Database Schema
```sql
CREATE TABLE words (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    word TEXT NOT NULL,
    definition TEXT,
    pronunciation TEXT,
    partOfSpeech TEXT,
    source TEXT,
    copiedText TEXT,
    dateAdded INTEGER,
    isFavorite INTEGER DEFAULT 0,
    usageCount INTEGER DEFAULT 1
);
```

### API Endpoints
- `GET /api/v2/entries/en/{word}` - Get word definition
- Response includes phonetics, meanings, and examples

### Background Service
- Uses Android's ClipboardManager
- Monitors primary clip changes
- Processes changes asynchronously
- Shows persistent notification

## Support

For issues, questions, or feature requests:
- Check the troubleshooting section
- Review the app permissions and settings
- Ensure you have the latest version
- Contact support through the app settings

## License

This is a custom-built application for personal use. The app uses open-source libraries and free APIs. Please respect the terms of service of the dictionary API provider.