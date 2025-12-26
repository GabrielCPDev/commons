package models.money

import java.math.BigDecimal

data class ExchangeRate(
    val from: Currency,
    val to: Currency,
    val rate: BigDecimal
) {
    init {
        require(rate > BigDecimal.ZERO) { "Exchange rate must be positive" }
        require(from != to) { "Exchange rate currencies must be different" }
    }
}
