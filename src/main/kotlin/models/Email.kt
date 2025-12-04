package models

@JvmInline
value class Email private constructor(val value: String) {
    init {
        require(value.isNotBlank()) { "Email cannot be blank" }
        require(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(value)) { "Invalid email format" }
    }

    companion object {
        fun of(value: String): Email = Email(value.lowercase())
    }

    override fun toString(): String = value
}