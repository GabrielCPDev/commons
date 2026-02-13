package messaging

data class Message<T>(
    val eventId: String,
    val tenantId: String,
    val aggregateId: String,
    val correlationId: String?,
    val payload: T
)