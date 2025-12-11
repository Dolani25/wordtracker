package com.wordtracker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.wordtracker.service.ClipboardService

class BootReceiver : BroadcastReceiver() {
    
    companion object {
        private const val TAG = "BootReceiver"
        private const val PREFS_NAME = "WordTrackerPrefs"
        private const val KEY_AUTO_START = "auto_start_enabled"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "BootReceiver received: ${intent.action}")
        
        if (Intent.ACTION_BOOT_COMPLETED == intent.action || 
            Intent.ACTION_MY_PACKAGE_REPLACED == intent.action) {
            
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val autoStart = prefs.getBoolean(KEY_AUTO_START, false)
            
            if (autoStart) {
                Log.d(TAG, "Auto-starting WordTracker service")
                startService(context)
            } else {
                Log.d(TAG, "Auto-start disabled, skipping service start")
            }
        }
    }

    private fun startService(context: Context) {
        val serviceIntent = Intent(context, ClipboardService::class.java).apply {
            action = ClipboardService.ACTION_START_SERVICE
        }
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}