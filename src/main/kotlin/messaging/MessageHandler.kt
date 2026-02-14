package messaging

interface MessageHandler<T> {
    suspend fun handle(
        message: Message<T>,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    )
}