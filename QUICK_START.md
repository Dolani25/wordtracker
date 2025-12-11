# WordTracker - Quick Start Guide

## 🚀 Ready-to-Build Android Project

This is a complete Android Studio project for WordTracker - a clipboard monitoring app that automatically collects words and their definitions.

## 📋 What You Get

- **Complete Android App** - All source code included
- **Modern Architecture** - Kotlin + Jetpack Compose + MVVM
- **Background Service** - Clipboard monitoring with notifications
- **Database Storage** - Room database for word collection
- **API Integration** - Free dictionary API for definitions
- **Material Design UI** - Clean, intuitive interface

## 🛠️ How to Build

### Option 1: Android Studio (Recommended)

1. **Download & Install Android Studio**
   - Get it from: https://developer.android.com/studio

2. **Open the Project**
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Select the `WordTracker_Project` folder
   - Click "OK"

3. **Wait for Sync**
   - Android Studio will automatically sync the project
   - This may take a few minutes (downloads dependencies)

4. **Build the APK**
   - Go to `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - Wait for build to complete
   - Find APK at: `app/build/outputs/apk/debug/app-debug.apk`

5. **Install on Device**
   - Enable "Install from Unknown Sources" on your phone
   - Copy APK to your device
   - Install and open the app

### Option 2: Command Line Build

If you have Android SDK installed:

```bash
# Navigate to project directory
cd WordTracker_Project

# Make gradlew executable (Linux/Mac)
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Or on Windows
gradlew.bat assembleDebug
```

## 📱 App Features

### Core Functionality
- ✅ **Background Clipboard Monitoring** - Runs until disabled
- ✅ **Smart Word Detection** - Extracts words from copied text
- ✅ **Automatic Definition Lookup** - Uses Free Dictionary API
- ✅ **Unlimited Storage** - No clipboard limit like your phone
- ✅ **Toggle Control** - Easy on/off for service
- ✅ **Smart Selection** - Intelligent word boundaries

### User Interface
- ✅ **Home Screen** - Service control and recent words
- ✅ **Words Screen** - Browse and search word collection
- ✅ **Favorites Screen** - Marked important words
- ✅ **Settings Screen** - App configuration

### Technical Features
- ✅ **Modern Android** - Kotlin + Jetpack Compose
- ✅ **Persistent Storage** - Room database
- ✅ **Background Service** - Efficient clipboard monitoring
- ✅ **Notifications** - Service status and controls
- ✅ **Material Design 3** - Modern UI components

## 🔧 Project Structure

```
WordTracker_Project/
├── app/
│   ├── src/main/java/com/wordtracker/
│   │   ├── MainActivity.kt          # Main UI with Compose
│   │   ├── WordTrackerApplication.kt # Application class
│   │   ├── data/                    # Database models and DAOs
│   │   ├── repository/              # Data repository
│   │   ├── service/                 # Background clipboard service
│   │   ├── viewmodel/               # MVVM ViewModels
│   │   ├── ui/                      # Compose UI screens
│   │   └── api/                     # Dictionary API client
│   ├── src/main/res/                # Resources (strings, themes, etc.)
│   └── build.gradle                 # App build configuration
├── build.gradle                     # Project build configuration
├── settings.gradle                  # Project settings
└── gradle.properties               # Gradle properties
```

## 📖 Usage Instructions

1. **Install the app** (using APK from build)
2. **Open the app** and grant necessary permissions
3. **Enable service** - Toggle "Background Service" on Home screen
4. **Start reading** - Use any app (browser, e-reader, social media)
5. **Copy text** - Long-press and copy words or sentences
6. **Words auto-collect** - App detects and stores with definitions
7. **Browse collection** - Use Words tab to view and search

## 🔐 Permissions Required

- **INTERNET** - For definition lookup
- **FOREGROUND_SERVICE** - For background operation
- **RECEIVE_BOOT_COMPLETED** - For auto-start option
- **VIBRATE** - For user feedback

## 🎯 Smart Features

- **Word Extraction** - Automatically extracts individual words from copied text
- **Definition Lookup** - Fetches meanings from Free Dictionary API
- **Usage Tracking** - Counts how often words are encountered
- **Favorites System** - Mark important words for quick access
- **Search & Filter** - Find specific words in your collection
- **Export Capability** - Backup your word collection

## 🛡️ Privacy & Security

- **Local Storage** - All words stored on your device
- **No Personal Data** - Only collects words you copy
- **Free API** - Uses public dictionary API (no registration required)
- **Open Source** - Full source code available for review

## 🐛 Troubleshooting

**If build fails:**
- Make sure you have Android Studio Arctic Fox or later
- Check that Android SDK 34 is installed
- Ensure Kotlin plugin is up to date
- Try "File" → "Invalidate Caches and Restart"

**If app doesn't work:**
- Check that all permissions are granted
- Ensure background service is enabled
- Verify internet connection for definitions
- Restart the app and toggle service

## 📊 Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Database**: Room (SQLite)
- **Networking**: Retrofit
- **Background Processing**: Coroutines
- **Build Tool**: Gradle
- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34 (Android 14)

## 🎨 Design

- **Material Design 3** - Modern Android design system
- **Dark/Light Theme** - Automatic system theme support
- **Responsive Layout** - Works on phones and tablets
- **Accessibility** - Supports screen readers and large text

## 🚀 Next Steps

1. **Build the app** using Android Studio
2. **Install on your device** and test functionality
3. **Enable the service** and start reading
4. **Copy words** while reading articles, books, etc.
5. **Build your vocabulary** with automatic collection

## 📞 Support

If you encounter issues:
- Check the BUILD_GUIDE.md for detailed instructions
- Review app permissions in Settings
- Try restarting the app and service
- All source code is included for customization

---

**Happy vocabulary building!** 📚✨