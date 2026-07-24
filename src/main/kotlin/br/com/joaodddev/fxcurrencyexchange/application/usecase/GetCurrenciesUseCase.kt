package br.com.joaodddev.fxcurrencyexchange.application.usecase

import br.com.joaodddev.fxcurrencyexchange.domain.repository.CurrencyRepository
import br.com.joaodddev.fxcurrencyexchange.web.dto.CurrencyResponse
import org.springframework.stereotype.Service

@Service
class GetCurrenciesUseCase(
    private val currencyRepository: CurrencyRepository
) {
    fun execute(): List<CurrencyResponse> =
        currencyRepository.findAllActive()
            .map { CurrencyResponse.from(it) }
}