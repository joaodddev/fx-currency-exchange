package br.com.joaodddev.fxcurrencyexchange.infrastructure.external.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class OpenExchangeRatesResponse(
    @JsonProperty("disclaimer") val disclaimer: String? = null,
    @JsonProperty("license") val license: String? = null,
    @JsonProperty("timestamp") val timestamp: Long = 0,
    @JsonProperty("base") val base: String = "USD",
    @JsonProperty("rates") val rates: Map<String, BigDecimal> = emptyMap()
)