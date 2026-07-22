package br.com.joaodddev.fxcurrencyexchange.domain.service

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate
import br.com.joaodddev.fxcurrencyexchange.domain.valueobject.Money
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ExchangeRateDomainService {

    fun convert(amount: Money, rate: ExchangeRate): Money {
        require(amount.currency == rate.fromCurrency) {
            "Currency mismatch: expected ${rate.fromCurrency}, got ${amount.currency}"
        }
        return amount.convertWith(rate.rate, rate.toCurrency)
    }

    fun calculateCrossRate(
        baseToUsd: BigDecimal,
        targetToUsd: BigDecimal
    ): BigDecimal {
        require(baseToUsd > BigDecimal.ZERO) { "Base rate must be positive" }
        return targetToUsd.divide(baseToUsd, 8, java.math.RoundingMode.HALF_UP)
    }
}