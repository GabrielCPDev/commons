package messaging.notifications

import models.ID

class Notification<T>(
    id: ID,
    channels: List<NotificationChannel>,
    val data: T
) : Alert(id, channels)