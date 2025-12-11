package com.wordtracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.wordtracker.data.WordDatabase
import com.wordtracker.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordTrackerApplication : Application() {
    
    private val applicationScope = CoroutineScope(SupervisorJob())
    
    val database by lazy { WordDatabase.getDatabase(this) }
    val repository by lazy { WordRepository(database.wordDao(), applicationScope) }

    override fun onCreate() {
        super.onCreate()
        instance = this
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "WordTracker Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows when WordTracker is monitoring clipboard"
                setShowBadge(false)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "WordTrackerServiceChannel"
        lateinit var instance: WordTrackerApplication
            private set
    }
}