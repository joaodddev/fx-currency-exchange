package br.com.joaodddev.fxcurrencyexchange.application.usecase

import br.com.joaodddev.fxcurrencyexchange.domain.repository.ConversionHistoryRepository
import br.com.joaodddev.fxcurrencyexchange.web.dto.ConversionHistoryResponse
import org.springframework.stereotype.Service

@Service
class GetConversionHistoryUseCase(
    private val conversionHistoryRepository: ConversionHistoryRepository
) {
    fun execute(): List<ConversionHistoryResponse> =
        conversionHistoryRepository.findAll()
            .map { ConversionHistoryResponse.from(it) }

    fun executeByUser(userId: Long): List<ConversionHistoryResponse> =
        conversionHistoryRepository.findByUserId(userId)
            .map { ConversionHistoryResponse.from(it) }
}