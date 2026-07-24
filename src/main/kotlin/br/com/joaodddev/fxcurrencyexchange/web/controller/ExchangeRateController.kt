package br.com.joaodddev.fxcurrencyexchange.web.controller

import br.com.joaodddev.fxcurrencyexchange.application.usecase.FetchExchangeRatesUseCase
import br.com.joaodddev.fxcurrencyexchange.application.usecase.GetExchangeRateUseCase
import br.com.joaodddev.fxcurrencyexchange.web.dto.ExchangeRateResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/rates")
@Tag(name = "Exchange Rates", description = "FX rate operations")
class ExchangeRateController(
    private val getExchangeRateUseCase: GetExchangeRateUseCase,
    private val fetchExchangeRatesUseCase: FetchExchangeRatesUseCase
) {

    @GetMapping
    @Operation(summary = "Get exchange rate between two currencies")
    fun getRate(
        @RequestParam from: String,
        @RequestParam to: String
    ): ResponseEntity<ExchangeRateResponse> =
        ResponseEntity.ok(getExchangeRateUseCase.execute(from, to))

    @PostMapping("/refresh")
    @Operation(summary = "Force refresh of exchange rates from external API")
    fun refresh(): ResponseEntity<Map<String, Any>> {
        val count = fetchExchangeRatesUseCase.execute()
        return ResponseEntity.ok(mapOf("message" to "Rates refreshed successfully", "count" to count))
    }
}