data class Message(val content: String, val type: MessageType)

enum class MessageType {
    USER,
    AI
}
