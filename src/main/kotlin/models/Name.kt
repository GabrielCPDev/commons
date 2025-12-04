package models

class Name private constructor(val value: String) {

    init {
        require(value.isNotBlank()) { "Name cannot be empty" }
        require(value.length >= 3) { "Name must have at least 3 characters" }
        require(value.length <= 255) { "Name cannot exceed 255 characters" }
    }

    companion object {
        fun of(value: String): Name = Name(value)
    }

    override fun toString(): String = value
}