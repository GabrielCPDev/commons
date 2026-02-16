package models

@JvmInline
value class Phone private constructor(val value: String) {

    init {
        require(value.isNotBlank()) { "Phone cannot be blank" }
    }

    companion object {
        fun of(raw: String): Phone {
            val normalized = raw.trim()
            return Phone(normalized)
        }
    }

    override fun toString(): String = value
}
