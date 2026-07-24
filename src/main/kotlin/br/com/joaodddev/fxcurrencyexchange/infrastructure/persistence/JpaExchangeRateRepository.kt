package br.com.joaodddev.fxcurrencyexchange.infrastructure.persistence

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ExchangeRate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaExchangeRateRepository : JpaRepository<ExchangeRate, Long> {
    fun findByFromCurrencyAndToCurrency(from: String, to: String): ExchangeRate?
    fun findAllByFromCurrency(from: String): List<ExchangeRate>
    fun deleteByFromCurrencyAndToCurrency(from: String, to: String)
}