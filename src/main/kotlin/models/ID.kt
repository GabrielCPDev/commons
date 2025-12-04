package models

import java.util.UUID

data class ID(val value: UUID) {

    constructor(value: String) : this(UUID.fromString(value))

    override fun toString(): String {
        return value.toString()
    }
    companion object {
        fun generate(): ID = ID(UUID.randomUUID())
        fun from(id: String): ID = ID(id)
        fun from(id: UUID): ID = ID(id)
    }
}