# WordTracker - Android App

An intelligent Android app that monitors your clipboard, automatically collects new words, and fetches their definitions to help you build your vocabulary.

## 🚀 Features

- **Background Clipboard Monitoring** - Continuously monitors clipboard changes
- **Smart Word Detection** - Automatically extracts individual words from copied text
- **Definition Lookup** - Fetches word meanings using Free Dictionary API
- **Persistent Storage** - Stores unlimited words locally
- **Modern UI** - Built with Jetpack Compose and Material Design 3
- **Toggle Control** - Easy on/off switching for the service
- **Search & Filter** - Find specific words in your collection
- **Favorites System** - Mark important words

## 📱 Screens

- **Home** - Service control and recent words
- **Words** - Browse and search your word collection
- **Favorites** - Your marked important words
- **Settings** - App configuration and data management

## 🛠️ Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository pattern
- **Database**: Room (SQLite)
- **Networking**: Retrofit
- **Background Processing**: Coroutines
- **Minimum SDK**: API 21 (Android 5.0)

## 📦 How to Get the APK

### Method 1: Download from GitHub Actions (Recommended)

1. Go to the "Actions" tab in this repository
2. Click on the latest workflow run
3. Scroll down to "Artifacts" section
4. Download "WordTracker-APK" artifact
5. Install the APK on your Android device

### Method 2: Build Locally

1. Clone this repository
2. Open in Android Studio
3. Build → Build Bundle(s) / APK(s) → Build APK(s)
4. Find APK at `app/build/outputs/apk/debug/app-debug.apk`

## 🎯 Usage

1. **Install the APK** on your Android device
2. **Open the app** and grant necessary permissions
3. **Enable service** - Toggle "Background Service" on Home screen
4. **Start reading** - Use any app (browser, e-reader, etc.)
5. **Copy words** - Long-press and copy text while reading
6. **Words auto-collect** - App detects and stores with definitions
7. **Browse collection** - Use Words tab to view and search

## 🔐 Permissions Required

- **INTERNET**: For definition lookup
- **FOREGROUND_SERVICE**: For background operation
- **RECEIVE_BOOT_COMPLETED**: For auto-start option
- **VIBRATE**: For user feedback

## 🏗️ Project Structure

```
app/src/main/java/com/wordtracker/
├── MainActivity.kt          # Main UI with Compose
├── WordTrackerApplication.kt # Application class
├── data/                    # Database models and DAOs
├── repository/              # Data repository
├── service/                 # Background clipboard service
├── viewmodel/               # MVVM ViewModels
├── ui/                      # Compose UI screens
└── api/                     # Dictionary API client
```

## 🔧 Configuration

The app uses the Free Dictionary API (https://dictionaryapi.dev/):
- No API key required
- Free tier with rate limits
- Supports English words only

## 🚀 Development

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 34
- Kotlin 1.9.22
- Gradle 8.2

### Building

```bash
# Clone the repository
git clone https://github.com/yourusername/wordtracker.git
cd wordtracker

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease
```

## 📝 License

This project is created for educational and personal use. It uses open-source libraries and the Free Dictionary API.

## 🤝 Contributing

Feel free to submit issues and enhancement requests!

## 📞 Support

If you encounter any issues:
1. Check if all permissions are granted
2. Ensure the background service is enabled
3. Verify internet connection for definitions
4. Try restarting the app and service

## 🎉 Acknowledgments

- [Free Dictionary API](https://dictionaryapi.dev/) for word definitions
- [Android Jetpack](https://developer.android.com/jetpack) libraries
- [Material Design](https://material.io/design) for UI components

---

**Build your vocabulary effortlessly with WordTracker!** 📚✨