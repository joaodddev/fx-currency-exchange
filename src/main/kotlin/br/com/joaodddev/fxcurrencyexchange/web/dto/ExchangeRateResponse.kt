package br.com.joaodddev.fxcurrencyexchange.web.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ExchangeRateResponse(
    val from: String,
    val to: String,
    val rate: BigDecimal,
    val fetchedAt: LocalDateTime,
    val cached: Boolean
)