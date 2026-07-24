package br.com.joaodddev.fxcurrencyexchange.domain.service

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate
import br.com.joaodddev.fxcurrencyexchange.domain.valueobject.Money
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime

class ExchangeRateDomainServiceTest {

    private val domainService = ExchangeRateDomainService()

    @Test
    fun `should convert money using rate`() {
        val money = Money(BigDecimal("100.00"), "USD")
        val rate = buildRate("USD", "BRL", BigDecimal("5.25"))

        val result = domainService.convert(money, rate)

        assertEquals(BigDecimal("525.00"), result.amount)
        assertEquals("BRL", result.currency)
    }

    @Test
    fun `should throw when currency mismatch`() {
        val money = Money(BigDecimal("100.00"), "EUR")
        val rate = buildRate("USD", "BRL", BigDecimal("5.25"))

        assertThrows<IllegalArgumentException> {
            domainService.convert(money, rate)
        }
    }

    @Test
    fun `should calculate cross rate correctly`() {
        val baseToUsd = BigDecimal("1.00")
        val targetToUsd = BigDecimal("5.25")

        val crossRate = domainService.calculateCrossRate(baseToUsd, targetToUsd)

        assertEquals(BigDecimal("5.25000000"), crossRate)
    }

    @Test
    fun `should throw when base rate is zero`() {
        assertThrows<IllegalArgumentException> {
            domainService.calculateCrossRate(BigDecimal.ZERO, BigDecimal("5.25"))
        }
    }

    private fun buildRate(from: String, to: String, rate: BigDecimal) = ExchangeRate(
        fromCurrency = from,
        toCurrency = to,
        rate = rate,
        fetchedAt = LocalDateTime.now()
    )
}