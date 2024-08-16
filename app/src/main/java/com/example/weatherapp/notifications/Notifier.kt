package com.example.weatherapp.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.weatherapp.R

/**
 * Class responsible for displaying weather notifications.
 *
 * @property notificationManager The NotificationManager used to manage notifications.
 * @property context The application context used to build the notifications.
 */
class Notifier(
    private val notificationManager: NotificationManager,
    private val context: Context
) {
    private val notificationChannelId: String = "runner_channel_id"
    private val notificationChannelName: String = "Running Notification"
    private val notificationId: Int = 200

    /**
     * Displays the notification with the specified title, content, and icon.
     *
     * @param title The title of the notification.
     * @param content The content text of the notification.
     * @param icon The icon resource ID for the notification.
     */
    fun showNotification(title: String, content: String, icon: Int) {
        val channel = createNotificationChannel()
        notificationManager.createNotificationChannel(channel)
        
        val notification = buildNotification(title, content, icon)
        notificationManager.notify(
            notificationId,
            notification
        )
    }

    /**
     * Creates a notification channel.
     *
     * @return The created NotificationChannel.
     */
    private fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    /**
     * Builds the notification with the specified title, content, and icon.
     *
     * @param title The title of the notification.
     * @param content The content text of the notification.
     * @param icon The icon resource ID for the notification.
     * @return The built Notification.
     */
    private fun buildNotification(title: String, content: String, icon: Int): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}