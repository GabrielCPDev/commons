package models

import utils.InvalidQuantityException
import java.math.BigDecimal
import java.math.RoundingMode

@ConsistentCopyVisibility
data class Quantity private constructor(
    val value: BigDecimal
) {
    init {
        if (value < BigDecimal.ZERO) {
            throw InvalidQuantityException("Quantity cannot be negative: $value")
        }
    }

    companion object {
        private const val DEFAULT_SCALE = 4
        private val ROUNDING = RoundingMode.HALF_UP

        fun of(value: BigDecimal, scale: Int = DEFAULT_SCALE): Quantity =
            Quantity(value.setScale(scale, ROUNDING))

        fun of(value: String, scale: Int = DEFAULT_SCALE): Quantity =
            Quantity(value.toBigDecimal().setScale(scale, ROUNDING))

        fun of(value: Double, scale: Int = DEFAULT_SCALE): Quantity =
            Quantity(value.toBigDecimal().setScale(scale, ROUNDING))

        fun zero(scale: Int = DEFAULT_SCALE): Quantity =
            Quantity(BigDecimal.ZERO.setScale(scale, ROUNDING))
    }

    operator fun plus(other: Quantity): Quantity =
        of(this.value.add(other.value), this.value.scale())

    operator fun minus(other: Quantity): Quantity =
        of(this.value.subtract(other.value), this.value.scale())

    operator fun compareTo(other: Quantity): Int =
        this.value.compareTo(other.value)

    fun isZero(): Boolean = this.value.signum() == 0

    fun isGreaterThan(other: Quantity): Boolean = this > other
    fun isLessThan(other: Quantity): Boolean = this < other
    fun isGreaterOrEqualTo(other: Quantity): Boolean = this >= other

    override fun toString(): String = value.toPlainString()
}
