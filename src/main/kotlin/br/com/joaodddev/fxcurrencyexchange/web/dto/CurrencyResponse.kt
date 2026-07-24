package br.com.joaodddev.fxcurrencyexchange.web.dto

import br.com.joaodddev.fxcurrencyexchange.domain.entity.Currency

data class CurrencyResponse(
    val code: String,
    val name: String,
    val symbol: String?,
    val active: Boolean
) {
    companion object {
        fun from(currency: Currency) = CurrencyResponse(
            code = currency.code,
            name = currency.name,
            symbol = currency.symbol,
            active = currency.active
        )
    }
}