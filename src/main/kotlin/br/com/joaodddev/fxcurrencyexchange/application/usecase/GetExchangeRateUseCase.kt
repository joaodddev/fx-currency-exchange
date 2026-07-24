package br.com.joaodddev.fxcurrencyexchange.application.usecase

import br.com.joaodddev.fxcurrencyexchange.domain.repository.ExchangeRateRepository
import br.com.joaodddev.fxcurrencyexchange.infrastructure.cache.ExchangeRateCacheService
import br.com.joaodddev.fxcurrencyexchange.web.dto.ExchangeRateResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GetExchangeRateUseCase(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val cacheService: ExchangeRateCacheService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun execute(from: String, to: String): ExchangeRateResponse {
        val fromUpper = from.uppercase()
        val toUpper = to.uppercase()

        val cachedRate = cacheService.getCachedRate(fromUpper, toUpper)
        if (cachedRate != null) {
            log.debug("Cache hit for $fromUpper->$toUpper")
            return ExchangeRateResponse(
                from = fromUpper,
                to = toUpper,
                rate = cachedRate,
                fetchedAt = LocalDateTime.now(),
                cached = true
            )
        }

        log.debug("Cache miss for $fromUpper->$toUpper, querying database")
        val rate = exchangeRateRepository.findByFromCurrencyAndToCurrency(fromUpper, toUpper)
            ?: throw NoSuchElementException("Exchange rate not found for $fromUpper -> $toUpper")

        cacheService.cacheRate(fromUpper, toUpper, rate.rate)

        return ExchangeRateResponse(
            from = rate.fromCurrency,
            to = rate.toCurrency,
            rate = rate.rate,
            fetchedAt = rate.fetchedAt,
            cached = false
        )
    }
}