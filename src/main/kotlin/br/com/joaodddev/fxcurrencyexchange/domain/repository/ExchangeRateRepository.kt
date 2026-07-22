package br.com.joaodddev.fxcurrencyexchange.domain.repository

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate

interface ExchangeRateRepository {
    fun findByFromCurrencyAndToCurrency(from: String, to: String): ExchangeRate?
    fun findAllByFromCurrency(from: String): List<ExchangeRate>
    fun save(exchangeRate: ExchangeRate): ExchangeRate
    fun saveAll(rates: List<ExchangeRate>): List<ExchangeRate>
    fun deleteByFromCurrencyAndToCurrency(from: String, to: String)
}