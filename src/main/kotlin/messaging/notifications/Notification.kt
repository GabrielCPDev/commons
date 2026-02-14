package messaging.notifications

sealed class Notification {
    data class Email(
        val address: String,
        val subject: String,
        val body: String
    ) : Notification()

    data class Sms(
        val phoneNumber: String,
        val text: String
    ) : Notification()

    data class WhatsApp(
        val phoneNumber: String,
        val text: String
    ) : Notification()

    data class Push(
        val deviceToken: String,
        val title: String,
        val body: String
    ) : Notification()
}