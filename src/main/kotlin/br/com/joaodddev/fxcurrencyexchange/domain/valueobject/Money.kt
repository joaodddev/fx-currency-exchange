package br.com.joaodddev.fxcurrencyexchange.domain.valueobject

import java.math.BigDecimal
import java.math.RoundingMode

data class Money(
    val amount: BigDecimal,
    val currency: String
) {
    init {
        require(amount >= BigDecimal.ZERO) { "Amount must be non-negative" }
        require(currency.length == 3) { "Currency code must be 3 characters" }
    }

    fun convertWith(rate: BigDecimal, targetCurrency: String): Money =
        Money(
            amount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP),
            currency = targetCurrency
        )

    override fun toString(): String = "${currency} ${amount.setScale(2, RoundingMode.HALF_UP)}"
}