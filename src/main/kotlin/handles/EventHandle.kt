package handles

interface EventHandle<T> {
    suspend fun handle(event: T)
}