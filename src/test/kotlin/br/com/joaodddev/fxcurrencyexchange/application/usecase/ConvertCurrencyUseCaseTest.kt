package br.com.joaodddev.fxcurrencyexchange.application.usecase

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ConversionHistory
import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ConversionHistoryRepository
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ExchangeRateRepository
import br.com.joaodddev.fxcurrencyexchange.domain.service.ExchangeRateDomainService
import br.com.joaodddev.fxcurrencyexchange.infrastructure.cache.ExchangeRateCacheService
import br.com.joaodddev.fxcurrencyexchange.web.dto.ConversionRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime

class ConvertCurrencyUseCaseTest {

    private val exchangeRateRepository = mockk<ExchangeRateRepository>()
    private val conversionHistoryRepository = mockk<ConversionHistoryRepository>()
    private val cacheService = mockk<ExchangeRateCacheService>()
    private val domainService = ExchangeRateDomainService()

    private val useCase = ConvertCurrencyUseCase(
        exchangeRateRepository,
        conversionHistoryRepository,
        cacheService,
        domainService
    )

    @Test
    fun `should convert using cached rate`() {
        val request = ConversionRequest("USD", "BRL", BigDecimal("100.00"))

        every { cacheService.getCachedRate("USD", "BRL") } returns BigDecimal("5.25")
        every { conversionHistoryRepository.save(any()) } returns buildHistory()

        val result = useCase.execute(request)

        assertEquals(BigDecimal("525.00"), result.convertedAmount)
        assertEquals("CACHE", result.source)
        verify(exactly = 0) { exchangeRateRepository.findByFromCurrencyAndToCurrency(any(), any()) }
    }

    @Test
    fun `should convert using database rate when cache misses`() {
        val request = ConversionRequest("USD", "BRL", BigDecimal("100.00"))

        every { cacheService.getCachedRate("USD", "BRL") } returns null
        every { exchangeRateRepository.findByFromCurrencyAndToCurrency("USD", "BRL") } returns buildRate()
        every { cacheService.cacheRate("USD", "BRL", any()) } returns Unit
        every { conversionHistoryRepository.save(any()) } returns buildHistory()

        val result = useCase.execute(request)

        assertEquals(BigDecimal("525.00"), result.convertedAmount)
        assertEquals("DATABASE", result.source)
        verify(exactly = 1) { cacheService.cacheRate("USD", "BRL", any()) }
    }

    @Test
    fun `should throw when rate not found`() {
        val request = ConversionRequest("USD", "XYZ", BigDecimal("100.00"))

        every { cacheService.getCachedRate("USD", "XYZ") } returns null
        every { exchangeRateRepository.findByFromCurrencyAndToCurrency("USD", "XYZ") } returns null

        assertThrows<NoSuchElementException> {
            useCase.execute(request)
        }
    }

    @Test
    fun `should persist conversion history`() {
        val request = ConversionRequest("USD", "BRL", BigDecimal("100.00"))

        every { cacheService.getCachedRate("USD", "BRL") } returns BigDecimal("5.25")
        every { conversionHistoryRepository.save(any()) } returns buildHistory()

        useCase.execute(request)

        verify(exactly = 1) { conversionHistoryRepository.save(any()) }
    }

    @Test
    fun `should uppercase currency codes`() {
        val request = ConversionRequest("usd", "brl", BigDecimal("100.00"))

        every { cacheService.getCachedRate("USD", "BRL") } returns BigDecimal("5.25")
        every { conversionHistoryRepository.save(any()) } returns buildHistory()

        val result = useCase.execute(request)

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

    private fun buildHistory() = ConversionHistory(
        id = 1L,
        fromCurrency = "USD",
        toCurrency = "BRL",
        amount = BigDecimal("100.00"),
        convertedAmount = BigDecimal("525.00"),
        rate = BigDecimal("5.25")
    )
}