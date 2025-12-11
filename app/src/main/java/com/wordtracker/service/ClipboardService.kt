package com.wordtracker.service

import android.app.*
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.wordtracker.MainActivity
import com.wordtracker.R
import com.wordtracker.WordTrackerApplication
import com.wordtracker.repository.WordRepository
import kotlinx.coroutines.*

class ClipboardService : Service() {
    
    private lateinit var clipboardManager: ClipboardManager
    private lateinit var repository: WordRepository
    private var serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var isMonitoring = false
    
    companion object {
        private const val TAG = "ClipboardService"
        private const val NOTIFICATION_ID = 101
        const val ACTION_TOGGLE_SERVICE = "com.wordtracker.TOGGLE_SERVICE"
        const val ACTION_START_SERVICE = "com.wordtracker.START_SERVICE"
        const val ACTION_STOP_SERVICE = "com.wordtracker.STOP_SERVICE"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "ClipboardService created")
        
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        repository = WordTrackerApplication.instance.repository
        
        startForeground(NOTIFICATION_ID, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "ClipboardService started with action: ${intent?.action}")
        
        when (intent?.action) {
            ACTION_START_SERVICE -> startMonitoring()
            ACTION_STOP_SERVICE -> stopMonitoring()
            ACTION_TOGGLE_SERVICE -> toggleMonitoring()
            else -> startMonitoring()
        }
        
        return START_STICKY
    }

    private fun startMonitoring() {
        if (isMonitoring) return
        
        isMonitoring = true
        clipboardManager.addPrimaryClipChangedListener(clipboardListener)
        Log.d(TAG, "Started monitoring clipboard")
        
        updateNotification("WordTracker is active - monitoring clipboard")
        showToast("WordTracker monitoring started")
    }

    private fun stopMonitoring() {
        if (!isMonitoring) return
        
        isMonitoring = false
        clipboardManager.removePrimaryClipChangedListener(clipboardListener)
        Log.d(TAG, "Stopped monitoring clipboard")
        
        updateNotification("WordTracker is paused")
        showToast("WordTracker monitoring stopped")
    }

    private fun toggleMonitoring() {
        if (isMonitoring) {
            stopMonitoring()
        } else {
            startMonitoring()
        }
    }

    private val clipboardListener = ClipboardManager.OnPrimaryClipChangedListener {
        serviceScope.launch {
            try {
                val clip = clipboardManager.primaryClip
                if (clip != null && clip.itemCount > 0) {
                    val copiedText = clip.getItemAt(0).text?.toString()
                    if (!copiedText.isNullOrBlank()) {
                        Log.d(TAG, "Clipboard changed: $copiedText")
                        
                        val source = detectSourceApp()
                        repository.addWordFromClipboard(copiedText, source)
                        
                        showToast("Word(s) added to collection")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing clipboard change", e)
            }
        }
    }

    private fun detectSourceApp(): String? {
        return try {
            val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            val runningTasks = activityManager.getRunningTasks(1)
            runningTasks.firstOrNull()?.topActivity?.packageName
        } catch (e: Exception) {
            null
        }
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, 
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val toggleIntent = Intent(this, ClipboardService::class.java).apply {
            action = ACTION_TOGGLE_SERVICE
        }
        val togglePendingIntent = PendingIntent.getService(
            this, 1, toggleIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, "WordTrackerServiceChannel")
            .setContentTitle("WordTracker")
            .setContentText("Monitoring clipboard for new words...")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .addAction(0, "Toggle", togglePendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification(text: String) {
        val notification = NotificationCompat.Builder(this, "WordTrackerServiceChannel")
            .setContentTitle("WordTracker")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_notification)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ClipboardService destroyed")
        
        if (isMonitoring) {
            clipboardManager.removePrimaryClipChangedListener(clipboardListener)
        }
        
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun isServiceMonitoring(): Boolean {
        return isMonitoring
    }
}