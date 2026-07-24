package br.com.joaodddev.fxcurrencyexchange.web.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class ConversionResponse(
    val from: String,
    val to: String,
    val amount: BigDecimal,
    val convertedAmount: BigDecimal,
    val rate: BigDecimal,
    val convertedAt: LocalDateTime,
    val source: String
)