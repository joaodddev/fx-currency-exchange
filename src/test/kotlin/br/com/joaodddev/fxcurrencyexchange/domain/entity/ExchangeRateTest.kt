package br.com.joaodddev.fxcurrencyexchange.domain.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class ExchangeRateTest {

    @Test
    fun `should convert amount correctly`() {
        val rate = buildRate(BigDecimal("5.25"))
        val result = rate.convert(BigDecimal("100.00"))
        assertEquals(BigDecimal("525.00"), result)
    }

    @Test
    fun `should round conversion to 2 decimal places`() {
        val rate = buildRate(BigDecimal("5.123456"))
        val result = rate.convert(BigDecimal("10.00"))
        assertEquals(BigDecimal("51.23"), result)
    }

    @Test
    fun `should return true when rate is stale`() {
        val rate = ExchangeRate(
            fromCurrency = "USD",
            toCurrency = "BRL",
            rate = BigDecimal("5.25"),
            fetchedAt = LocalDateTime.now().minusMinutes(60)
        )
        assertTrue(rate.isStale(30))
    }

    @Test
    fun `should return false when rate is fresh`() {
        val rate = ExchangeRate(
            fromCurrency = "USD",
            toCurrency = "BRL",
            rate = BigDecimal("5.25"),
            fetchedAt = LocalDateTime.now().minusMinutes(10)
        )
        assertFalse(rate.isStale(30))
    }

    private fun buildRate(rate: BigDecimal) = ExchangeRate(
        fromCurrency = "USD",
        toCurrency = "BRL",
        rate = rate,
        fetchedAt = LocalDateTime.now()
    )
}