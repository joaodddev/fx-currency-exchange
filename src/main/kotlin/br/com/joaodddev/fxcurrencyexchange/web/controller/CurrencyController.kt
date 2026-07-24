package br.com.joaodddev.fxcurrencyexchange.web.controller

import br.com.joaodddev.fxcurrencyexchange.application.usecase.GetCurrenciesUseCase
import br.com.joaodddev.fxcurrencyexchange.web.dto.CurrencyResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/currencies")
@Tag(name = "Currencies", description = "Supported currencies")
class CurrencyController(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
) {

    @GetMapping
    @Operation(summary = "List all supported currencies")
    fun listAll(): ResponseEntity<List<CurrencyResponse>> =
        ResponseEntity.ok(getCurrenciesUseCase.execute())
}