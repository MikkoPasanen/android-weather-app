package com.example.weatherapp.model

/**
 * Notification data.
 *
 * @property time the time when the notification should be displayed.
 * @property id unique id of the notification.
 */
data class NotificationItem(
    val time: Long,
    val id: Int
)