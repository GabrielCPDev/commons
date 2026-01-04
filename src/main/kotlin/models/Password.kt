package models

import java.util.regex.Pattern

@JvmInline
value class Password private constructor(val value: String) {

    companion object {
        private const val MIN_LENGTH = 8
        private val UPPERCASE = Pattern.compile("[A-Z]")
        private val LOWERCASE = Pattern.compile("[a-z]")
        private val DIGIT = Pattern.compile("[0-9]")
        private val SPECIAL = Pattern.compile("[^a-zA-Z0-9]")

        fun of(password: String): Password {
            validate(password)

            return Password(password)
        }

        private fun validate(password: String) {
            require(password.length >= MIN_LENGTH) { "Password must have at least $MIN_LENGTH characters" }
            require(UPPERCASE.matcher(password).find()) { "Password must contain at least one uppercase letter" }
            require(LOWERCASE.matcher(password).find()) { "Password must contain at least one lowercase letter" }
            require(DIGIT.matcher(password).find()) { "Password must contain at least one digit" }
            require(SPECIAL.matcher(password).find()) { "Password must contain at least one special character" }
        }

        fun fromHash(hash: String): Password = Password(hash)
    }

    override fun toString(): String = "********"
}