package br.com.joaodddev.fxcurrencyexchange.infrastructure.persistence

import br.com.joaodddev.fxcurrencyexchange.domain.entity.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaCurrencyRepository : JpaRepository<Currency, Long> {
    fun findByCode(code: String): Currency?
    fun findAllByActiveTrue(): List<Currency>
    fun existsByCode(code: String): Boolean
}