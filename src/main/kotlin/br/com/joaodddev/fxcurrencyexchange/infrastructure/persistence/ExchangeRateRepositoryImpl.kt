package br.com.joaodddev.fxcurrencyexchange.infrastructure.persistence

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ExchangeRateRepository
import org.springframework.stereotype.Component

@Component
class ExchangeRateRepositoryImpl(
    private val jpa: JpaExchangeRateRepository
) : ExchangeRateRepository {
    override fun findByFromCurrencyAndToCurrency(from: String, to: String): ExchangeRate? =
        jpa.findByFromCurrencyAndToCurrency(from, to)

    override fun findAllByFromCurrency(from: String): List<ExchangeRate> =
        jpa.findAllByFromCurrency(from)

    override fun save(exchangeRate: ExchangeRate): ExchangeRate =
        jpa.save(exchangeRate)

    override fun saveAll(rates: List<ExchangeRate>): List<ExchangeRate> =
        jpa.saveAll(rates)

    override fun deleteByFromCurrencyAndToCurrency(from: String, to: String) =
        jpa.deleteByFromCurrencyAndToCurrency(from, to)
}