package br.com.joaodddev.fxcurrencyexchange.domain.repository

import br.com.joaodddev.fxcurrencyexchange.domain.entity.Currency

interface CurrencyRepository {
    fun findAll(): List<Currency>
    fun findByCode(code: String): Currency?
    fun findAllActive(): List<Currency>
    fun existsByCode(code: String): Boolean
    fun save(currency: Currency): Currency
}