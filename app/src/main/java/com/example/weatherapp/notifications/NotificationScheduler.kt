package com.example.weatherapp.notifications

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.weatherapp.model.NotificationItem

/**
 * Class responsible for scheduling weather notifications using the AlarmManager.
 *
 * @property context The application context used to set up the AlarmManager.
 */
class NotificationScheduler(private val context: Context) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    /**
     * Creates a PendingIntent that will be sent to the AlarmReceiver.
     *
     * @param notificationItem The notification item containing the ID and time for the notification.
     * @return The created PendingIntent for the AlarmReceiver.
     */
    private fun createPendingIntent(notificationItem: NotificationItem): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)

        return PendingIntent.getBroadcast(
            context,
            notificationItem.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Schedules a repeating alarm to trigger daily notifications.
     *
     * @param notificationItem The notification item containing the ID and time for the notification.
     */
    fun schedule(notificationItem: NotificationItem) {
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            notificationItem.time,
            60000,
            createPendingIntent(notificationItem)
        )
    }
}