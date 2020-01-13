package com.jm.syncdata

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

class SyncDataApp: Application() {

  override fun onCreate() {
    super.onCreate()
    // provide custom configuration
    val config = Configuration.Builder()
      .setMinimumLoggingLevel(android.util.Log.INFO)
      .build()

    //initialize WorkManager
    WorkManager.initialize(this, config)
  }
}