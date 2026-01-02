package models

import java.util.regex.Pattern

@JvmInline
value class Password private constructor(val value: String) {

    init {
        require(value.length >= MIN_LENGTH) {
            "Password must have at least $MIN_LENGTH characters"
        }
        require(UPPERCASE.matcher(value).find()) {
            "Password must contain at least one uppercase letter"
        }
        require(LOWERCASE.matcher(value).find()) {
            "Password must contain at least one lowercase letter"
        }
        require(DIGIT.matcher(value).find()) {
            "Password must contain at least one digit"
        }
        require(SPECIAL.matcher(value).find()) {
            "Password must contain at least one special character"
        }
    }

    companion object {
        private const val MIN_LENGTH = 8

        private val UPPERCASE = Pattern.compile("[A-Z]")
        private val LOWERCASE = Pattern.compile("[a-z]")
        private val DIGIT = Pattern.compile("[0-9]")
        private val SPECIAL = Pattern.compile("[^a-zA-Z0-9]")

        fun of(raw: String): Password =
            Password(raw)
    }

    override fun toString(): String = "********"
}