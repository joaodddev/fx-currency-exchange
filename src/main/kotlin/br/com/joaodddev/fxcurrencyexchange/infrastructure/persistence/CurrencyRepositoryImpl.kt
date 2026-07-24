package br.com.joaodddev.fxcurrencyexchange.infrastructure.persistence

import br.com.joaodddev.fxcurrencyexchange.domain.entity.Currency
import br.com.joaodddev.fxcurrencyexchange.domain.repository.CurrencyRepository
import org.springframework.stereotype.Component

@Component
class CurrencyRepositoryImpl(
    private val jpa: JpaCurrencyRepository
) : CurrencyRepository {
    override fun findAll(): List<Currency> = jpa.findAll()
    override fun findByCode(code: String): Currency? = jpa.findByCode(code)
    override fun findAllActive(): List<Currency> = jpa.findAllByActiveTrue()
    override fun existsByCode(code: String): Boolean = jpa.existsByCode(code)
    override fun save(currency: Currency): Currency = jpa.save(currency)
}