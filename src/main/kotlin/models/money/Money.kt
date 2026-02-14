package models.money

import java.math.BigDecimal
import java.math.RoundingMode

@ConsistentCopyVisibility
data class Money private constructor(
    val amount: BigDecimal,
    val currency: Currency
) {

    init {
        require(amount.scale() <= currency.scale) {
            "Money for ${currency.name} must have at most ${currency.scale} decimal places"
        }
    }

    fun isPositive(): Boolean = amount > BigDecimal.ZERO
    fun isZero(): Boolean = amount.compareTo(BigDecimal.ZERO) == 0
    fun isNegative(): Boolean = amount < BigDecimal.ZERO

    operator fun plus(other: Money): Money {
        require(currency == other.currency) {
            "Cannot add money with different currencies"
        }
        return of(amount.add(other.amount), currency)
    }

    operator fun minus(other: Money): Money {
        require(currency == other.currency) {
            "Cannot subtract money with different currencies"
        }
        return of(amount.subtract(other.amount), currency)
    }

    fun apply(rate: ExchangeRate): Money {
        require(currency == rate.from) {
            "Money currency ${currency.name} does not match rate source ${rate.from.name}"
        }

        val converted = amount.multiply(rate.rate)
        return of(converted, rate.to)
    }

    override fun toString(): String =
        "${currency.value} ${amount.toPlainString()}"

    companion object {

        fun of(amount: BigDecimal, currency: Currency): Money {
            val normalized = amount.setScale(currency.scale, RoundingMode.HALF_UP)
            return Money(normalized, currency)
        }

        fun zero(currency: Currency): Money =
            Money(BigDecimal.ZERO.setScale(currency.scale, RoundingMode.HALF_UP), currency)
    }
}
