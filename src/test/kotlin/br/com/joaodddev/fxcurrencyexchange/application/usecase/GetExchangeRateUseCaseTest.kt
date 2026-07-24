package br.com.joaodddev.fxcurrencyexchange.application.usecase

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ExchangeRateRepository
import br.com.joaodddev.fxcurrencyexchange.infrastructure.cache.ExchangeRateCacheService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime

class GetExchangeRateUseCaseTest {

    private val exchangeRateRepository = mockk<ExchangeRateRepository>()
    private val cacheService = mockk<ExchangeRateCacheService>()

    private val useCase = GetExchangeRateUseCase(exchangeRateRepository, cacheService)

    @Test
    fun `should return cached rate when available`() {
        every { cacheService.getCachedRate("USD", "BRL") } returns BigDecimal("5.25")

        val result = useCase.execute("USD", "BRL")

        assertTrue(result.cached)
        assertEquals(BigDecimal("5.25"), result.rate)
        verify(exactly = 0) { exchangeRateRepository.findByFromCurrencyAndToCurrency(any(), any()) }
    }

    @Test
    fun `should return database rate when cache misses`() {
        every { cacheService.getCachedRate("USD", "BRL") } returns null
        every { exchangeRateRepository.findByFromCurrencyAndToCurrency("USD", "BRL") } returns buildRate()
        every { cacheService.cacheRate("USD", "BRL", any()) } returns Unit

        val result = useCase.execute("USD", "BRL")

        assertFalse(result.cached)
        assertEquals(BigDecimal("5.25"), result.rate)
        verify(exactly = 1) { cacheService.cacheRate("USD", "BRL", any()) }
    }

    @Test
    fun `should throw when rate not found`() {
        every { cacheService.getCachedRate("USD", "XYZ") } returns null
        every { exchangeRateRepository.findByFromCurrencyAndToCurrency("USD", "XYZ") } returns null

        assertThrows<NoSuchElementException> {
            useCase.execute("USD", "XYZ")
        }
    }

    @Test
    fun `should uppercase currency codes`() {
        every { cacheService.getCachedRate("USD", "BRL") } returns BigDecimal("5.25")

        val result = useCase.execute("usd", "brl")

        assertEquals("USD", result.from)
        assertEquals("BRL", result.to)
    }

    private fun buildRate() = ExchangeRate(
        id = 1L,
        fromCurrency = "USD",
        toCurrency = "BRL",
        rate = BigDecimal("5.25"),
        fetchedAt = LocalDateTime.now()
    )
}