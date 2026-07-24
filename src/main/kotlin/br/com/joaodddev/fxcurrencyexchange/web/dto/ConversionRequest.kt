package br.com.joaodddev.fxcurrencyexchange.web.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class ConversionRequest(

    @field:NotBlank(message = "Source currency is required")
    val from: String,

    @field:NotBlank(message = "Target currency is required")
    val to: String,

    @field:Positive(message = "Amount must be positive")
    val amount: BigDecimal
)