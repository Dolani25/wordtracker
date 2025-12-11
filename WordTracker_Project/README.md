# WordTracker - Android Word Collection App

A sophisticated Android application that monitors clipboard for copied words, stores their definitions, and provides smart selection features for avid readers.

## Features

### Core Functionality
- **Background Clipboard Monitoring**: Continuously monitors clipboard for copied text
- **Word Detection**: Automatically detects and extracts individual words from copied text
- **Definition Lookup**: Fetches word meanings and definitions automatically
- **Smart Selection**: Intelligent word boundary detection and selection
- **Toggle Control**: Easy on/off switching for background monitoring

### Storage & Management
- **Persistent Storage**: All words and definitions saved locally
- **Unlimited History**: No limit on word collection (unlike phone clipboard)
- **Word Categories**: Organize words by source or custom categories
- **Search & Filter**: Find specific words or browse by date/source

### User Interface
- **Modern Material Design**: Clean, intuitive interface
- **Word Library**: Browse collected words with definitions
- **Quick Actions**: Copy, share, or delete words
- **Statistics**: Track reading progress and vocabulary growth

### Background Service
- **Battery Optimized**: Efficient background processing
- **Notification Control**: Visible status with quick toggle
- **Auto-Start**: Optionally start on device boot
- **Memory Efficient**: Minimal resource usage

## Technical Architecture

### Components
- **ClipboardService**: Background service for clipboard monitoring
- **WordDatabase**: Room database for word storage
- **DefinitionRepository**: API integration for word meanings
- **MainActivity**: User interface and controls

### Permissions Required
- `INTERNET`: For definition lookup
- `RECEIVE_BOOT_COMPLETED`: For auto-start functionality
- `FOREGROUND_SERVICE`: For background operation
- `VIBRATE`: For feedback (optional)

### Libraries Used
- AndroidX libraries
- Room database
- Retrofit for API calls
- Material Design components
- Coroutines for async operations

## Usage

1. **Enable Monitoring**: Toggle the background service on
2. **Read Content**: Copy words while reading articles, novels, etc.
3. **Auto-Collection**: Words are automatically detected and stored
4. **View Definitions**: Check meanings in the app
5. **Manage Collection**: Organize, search, or export your word library

## Installation

The app will be built as an APK file that can be installed on any Android device (API level 21+).

## Development Notes

- Built with Kotlin for modern Android development
- Follows MVVM architecture pattern
- Implements Android best practices
- Optimized for performance and battery life