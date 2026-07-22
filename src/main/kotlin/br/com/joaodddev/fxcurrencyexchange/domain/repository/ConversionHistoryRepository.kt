package br.com.joaodddev.fxcurrencyexchange.domain.repository

import br.com.joaodddev.fxcurrencyexchange.domain.entity.ConversionHistory

interface ConversionHistoryRepository {
    fun save(history: ConversionHistory): ConversionHistory
    fun findByUserId(userId: Long): List<ConversionHistory>
    fun findAll(): List<ConversionHistory>
}