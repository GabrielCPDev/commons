package handles

class EventDispatcher(
    private val handlers: Map<Class<*>, List<EventHandle<*>>>
) {

    @Suppress("UNCHECKED_CAST")
    suspend fun <T : Any> dispatch(event: T) {
        val list = handlers[event::class.java] ?: return

        for (handler in list) {
            (handler as EventHandle<T>).handle(event)
        }
    }
}
