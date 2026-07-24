package br.com.joaodddev.fxcurrencyexchange.infrastructure.persistence

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ConversionHistory
import br.com.joaodddev.fxcurrencyexchange.domain.repository.ConversionHistoryRepository
import org.springframework.stereotype.Component

@Component
class ConversionHistoryRepositoryImpl(
    private val jpa: JpaConversionHistoryRepository
) : ConversionHistoryRepository {
    override fun save(history: ConversionHistory): ConversionHistory = jpa.save(history)
    override fun findByUserId(userId: Long): List<ConversionHistory> = jpa.findByUserId(userId)
    override fun findAll(): List<ConversionHistory> = jpa.findAll()
}