package br.com.joaodddev.fxcurrencyexchange.web.dto

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ConversionHistory
import java.math.BigDecimal
import java.time.LocalDateTime

data class ConversionHistoryResponse(
    val id: Long?,
    val from: String,
    val to: String,
    val amount: BigDecimal,
    val convertedAmount: BigDecimal,
    val rate: BigDecimal,
    val convertedAt: LocalDateTime
) {
    companion object {
        fun from(history: ConversionHistory) = ConversionHistoryResponse(
            id = history.id,
            from = history.fromCurrency,
            to = history.toCurrency,
            amount = history.amount,
            convertedAmount = history.convertedAmount,
            rate = history.rate,
            convertedAt = history.convertedAt
        )
    }
}