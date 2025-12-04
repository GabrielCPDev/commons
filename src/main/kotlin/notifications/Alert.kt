package notifications

import models.ID
import java.time.Instant

abstract class Alert(
    val id: ID,
    val channels: List<NotificationChannel>,
    val createdAt: Instant = Instant.now()
) {
    init {
        require(channels.isNotEmpty()) {
            "Channels cannot be empty"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Alert) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}