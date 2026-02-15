package models

class Name private constructor(val value: String) {

    val shortValue: String by lazy { buildShortValue() }

    init {
        require(value.isNotBlank()) { "Name cannot be empty" }
        require(value.length >= 3) { "Name must have at least 3 characters" }
        require(value.length <= 255) { "Name cannot exceed 255 characters" }
    }

    private fun buildShortValue(): String {
        val parts = value.trim()
            .split("\\s+".toRegex())
            .filter { it.isNotBlank() }

        if (parts.size == 1) return parts.first()

        val firstName = parts.first()
        val middleNames = parts.drop(1).dropLast(1)
        val lastName = parts.last()

        val middleInitials = middleNames.joinToString(" ") {
            "${it.first().uppercaseChar()}."
        }

        val lastInitial = "${lastName.first().uppercaseChar()}."

        return when {
            middleNames.isEmpty() ->
                "$firstName $lastInitial"

            else ->
                "$firstName $middleInitials $lastInitial"
        }.replace("\\s+".toRegex(), " ").trim()
    }

    companion object {
        fun of(value: String): Name = Name(value)
    }

    override fun toString(): String = value
}