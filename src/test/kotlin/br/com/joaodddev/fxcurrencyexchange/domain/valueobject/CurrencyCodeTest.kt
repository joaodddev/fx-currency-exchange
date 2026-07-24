package br.com.joaodddev.fxcurrencyexchange.domain.valueobject

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CurrencyCodeTest {

    @Test
    fun `should create valid currency code`() {
        val code = CurrencyCode("USD")
        assertEquals("USD", code.value)
    }

    @Test
    fun `should throw when code is less than 3 characters`() {
        assertThrows<IllegalArgumentException> {
            CurrencyCode("US")
        }
    }

    @Test
    fun `should throw when code is more than 3 characters`() {
        assertThrows<IllegalArgumentException> {
            CurrencyCode("USDT")
        }
    }

    @Test
    fun `should throw when code is lowercase`() {
        assertThrows<IllegalArgumentException> {
            CurrencyCode("usd")
        }
    }

    @Test
    fun `should return value as string`() {
        assertEquals("BRL", CurrencyCode("BRL").toString())
    }
}