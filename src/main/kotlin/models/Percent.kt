package models

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Percent private constructor(
    val value: BigDecimal
) {

    init {
        require(value >= BigDecimal.ZERO) {
            "Percent cannot be negative"
        }
        require(value <= ONE_HUNDRED) {
            "Percent cannot exceed 100"
        }
        require(value.scale() <= 2) {
            "Percent supports up to 2 decimal places"
        }
    }

    fun toFraction(): BigDecimal =
        value.divide(ONE_HUNDRED)

    fun isZero(): Boolean =
        value.compareTo(BigDecimal.ZERO) == 0

    fun isHundred(): Boolean =
        value.compareTo(ONE_HUNDRED) == 0

    override fun toString(): String =
        "${value.toPlainString()}%"

    companion object {

        private val ONE_HUNDRED = BigDecimal("100")

        fun of(value: BigDecimal): Percent =
            Percent(value.setScale(2, RoundingMode.HALF_UP))

        fun zero(): Percent =
            Percent(BigDecimal.ZERO.setScale(2))

        fun hundred(): Percent =
            Percent(ONE_HUNDRED.setScale(2))
    }
}