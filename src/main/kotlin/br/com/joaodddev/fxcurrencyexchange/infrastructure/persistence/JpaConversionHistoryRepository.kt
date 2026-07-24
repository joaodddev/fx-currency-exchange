package br.com.joaodddev.fxcurrencyexchange.infrastructure.persistence

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ConversionHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaConversionHistoryRepository : JpaRepository<ConversionHistory, Long> {
    fun findByUserId(userId: Long): List<ConversionHistory>
}