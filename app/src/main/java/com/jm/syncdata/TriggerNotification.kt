package com.jm.syncdata

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class TriggerNotification(context: Context, title: String, body: String) {

  init {
    sendNotification(context, title, body)
  }

  private fun createNotificationChannel(
    context: Context,
    name: String,
    description: String
  ): String {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    val chanelId = UUID.randomUUID().toString()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val importance = NotificationManager.IMPORTANCE_HIGH
      val channel = NotificationChannel(chanelId, name, importance)
      channel.enableLights(true)
      channel.enableVibration(true)
      channel.description = description
      channel.lightColor = Color.BLUE
      channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

      val notificationManager = context.getSystemService(NotificationManager::class.java)
      notificationManager?.createNotificationChannel(channel)
    }

    return chanelId
  }

  //

  private fun sendNotification(context: Context, title: String, body: String) {

    // Create an Intent for the activity you want to start
    val resultIntent = Intent(context, ViewDetailsActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    // Create the TaskStackBuilder
    val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
      // Add the intent, which inflates the back stack
      addNextIntentWithParentStack(resultIntent)
      // Get the PendingIntent containing the entire back stack
      getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    val notificationManager = NotificationManagerCompat.from(context)
    val mBuilder =
      NotificationCompat.Builder(context, createNotificationChannel(context, title, body))
    val notificationId = (System.currentTimeMillis() and 0xfffffff).toInt()
    mBuilder.setDefaults(Notification.DEFAULT_ALL)
      .setTicker("Hearty365")
      .setContentTitle(title)
      .setContentText(body)
      .setContentIntent(resultPendingIntent)
      .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setSmallIcon(R.drawable.ic_student)
      .setContentInfo("Content Info")
      .setAutoCancel(true)

    notificationManager.notify(notificationId, mBuilder.build())
  }


}