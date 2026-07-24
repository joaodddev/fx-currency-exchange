package br.com.joaodddev.fxcurrencyexchange.application.usecase

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ConversionHistory
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ConversionHistoryRepository
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ExchangeRateRepository
import br.com.joaodddev.fxcurrencyexchange.domain.service.ExchangeRateDomainService
import br.com.joaodddev.fxcurrencyexchange.domain.valueobject.Money
import br.com.joaodddev.fxcurrencyexchange.infrastructure.cache.ExchangeRateCacheService
import br.com.joaodddev.fxcurrencyexchange.web.dto.ConversionRequest
import br.com.joaodddev.fxcurrencyexchange.web.dto.ConversionResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ConvertCurrencyUseCase(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val conversionHistoryRepository: ConversionHistoryRepository,
    private val cacheService: ExchangeRateCacheService,
    private val domainService: ExchangeRateDomainService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun execute(request: ConversionRequest, userId: Long? = null): ConversionResponse {
        val from = request.from.uppercase()
        val to = request.to.uppercase()

        log.info("Converting ${request.amount} $from -> $to")

        val cachedRate = cacheService.getCachedRate(from, to)
        val (rate, source) = if (cachedRate != null) {
            log.debug("Using cached rate for $from->$to")
            Pair(cachedRate, "CACHE")
        } else {
            log.debug("Cache miss, fetching rate from database")
            val dbRate = exchangeRateRepository.findByFromCurrencyAndToCurrency(from, to)
                ?: throw NoSuchElementException("Exchange rate not found for $from -> $to")
            cacheService.cacheRate(from, to, dbRate.rate)
            Pair(dbRate.rate, "DATABASE")
        }

        val money = Money(amount = request.amount, currency = from)
        val converted = money.convertWith(rate, to)

        val history = ConversionHistory(
            userId = userId,
            fromCurrency = from,
            toCurrency = to,
            amount = request.amount,
            convertedAmount = converted.amount,
            rate = rate,
            convertedAt = LocalDateTime.now()
        )
        conversionHistoryRepository.save(history)

        return ConversionResponse(
            from = from,
            to = to,
            amount = request.amount,
            convertedAmount = converted.amount,
            rate = rate,
            convertedAt = history.convertedAt,
            source = source
        )
    }
}