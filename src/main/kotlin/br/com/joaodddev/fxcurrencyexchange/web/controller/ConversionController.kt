package br.com.joaodddev.fxcurrencyexchange.web.controller

import br.com.joaodddev.fxcurrencyexchange.application.usecase.ConvertCurrencyUseCase
import br.com.joaodddev.fxcurrencyexchange.application.usecase.GetConversionHistoryUseCase
import br.com.joaodddev.fxcurrencyexchange.web.dto.ConversionHistoryResponse
import br.com.joaodddev.fxcurrencyexchange.web.dto.ConversionRequest
import br.com.joaodddev.fxcurrencyexchange.web.dto.ConversionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/conversions")
@Tag(name = "Conversions", description = "Currency conversion operations")
class ConversionController(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val getConversionHistoryUseCase: GetConversionHistoryUseCase
) {

    @PostMapping
    @Operation(summary = "Convert an amount between two currencies")
    fun convert(@RequestBody @Valid request: ConversionRequest): ResponseEntity<ConversionResponse> =
        ResponseEntity.ok(convertCurrencyUseCase.execute(request))

    @GetMapping("/history")
    @Operation(summary = "Get all conversion history")
    fun history(): ResponseEntity<List<ConversionHistoryResponse>> =
        ResponseEntity.ok(getConversionHistoryUseCase.execute())
}