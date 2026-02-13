package messaging.notifications

import java.time.Instant

data class Notification<T>(
    val id: String,
    val channels: List<NotificationChannel>,
    val data: T,
    val createdAt: Instant = Instant.now()
) {
    init {
        require(channels.isNotEmpty()) { "At least one channel must be provided" }
    }
}