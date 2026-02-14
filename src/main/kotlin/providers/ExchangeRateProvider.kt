package providers

import models.money.Currency
import models.money.ExchangeRate

interface ExchangeRateProvider {
    suspend fun findRate(
        from: Currency,
        to: Currency
    ): ExchangeRate?
}