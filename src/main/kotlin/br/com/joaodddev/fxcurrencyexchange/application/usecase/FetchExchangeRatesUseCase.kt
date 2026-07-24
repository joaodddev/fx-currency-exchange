package br.com.joaodddev.fxcurrencyexchange.application.usecase

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ExchangeRateRepository
import br.com.joaodddev.fxcurrencyexchange.infrastructure.external.OpenExchangeRatesClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class FetchExchangeRatesUseCase(
    private val client: OpenExchangeRatesClient,
    private val exchangeRateRepository: ExchangeRateRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun execute(): Int {
        log.info("Fetching and persisting latest exchange rates")

        val response = client.fetchLatestRates()
        val base = response.base
        val now = LocalDateTime.now()

        val rates = response.rates.map { (currency, rate) ->
            val existing = exchangeRateRepository
                .findByFromCurrencyAndToCurrency(base, currency)

            existing?.let {
                ExchangeRate(
                    id = it.id,
                    fromCurrency = base,
                    toCurrency = currency,
                    rate = rate,
                    fetchedAt = now
                )
            } ?: ExchangeRate(
                fromCurrency = base,
                toCurrency = currency,
                rate = rate,
                fetchedAt = now
            )
        }

        exchangeRateRepository.saveAll(rates)
        log.info("Persisted ${rates.size} exchange rates")
        return rates.size
    }
}