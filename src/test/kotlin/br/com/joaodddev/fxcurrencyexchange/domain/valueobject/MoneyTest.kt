package br.com.joaodddev.fxcurrencyexchange.domain.valueobject

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class MoneyTest {

    @Test
    fun `should create money successfully`() {
        val money = Money(BigDecimal("100.00"), "USD")
        assertEquals(BigDecimal("100.00"), money.amount)
        assertEquals("USD", money.currency)
    }

    @Test
    fun `should throw when amount is negative`() {
        assertThrows<IllegalArgumentException> {
            Money(BigDecimal("-1.00"), "USD")
        }
    }

    @Test
    fun `should throw when currency code is invalid`() {
        assertThrows<IllegalArgumentException> {
            Money(BigDecimal("100.00"), "US")
        }
    }

    @Test
    fun `should convert with rate correctly`() {
        val money = Money(BigDecimal("100.00"), "USD")
        val result = money.convertWith(BigDecimal("5.25"), "BRL")
        assertEquals(BigDecimal("525.00"), result.amount)
        assertEquals("BRL", result.currency)
    }

    @Test
    fun `should round converted amount to 2 decimal places`() {
        val money = Money(BigDecimal("10.00"), "USD")
        val result = money.convertWith(BigDecimal("5.123456"), "BRL")
        assertEquals(BigDecimal("51.23"), result.amount)
    }

    @Test
    fun `should allow zero amount`() {
        assertDoesNotThrow {
            Money(BigDecimal.ZERO, "USD")
        }
    }
}