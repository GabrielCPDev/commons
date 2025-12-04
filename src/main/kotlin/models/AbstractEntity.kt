package models

import java.time.Instant

abstract class AbstractEntity<ID>(
    open val id: ID,
    open val createdAt: Instant,
    open val updatedAt: Instant
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractEntity<*>) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String {
        return "${this::class.simpleName}(id=$id)"
    }
}