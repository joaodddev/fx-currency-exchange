package br.com.joaodddev.fxcurrencyexchange.infrastructure.external

import br.com.joaodddev.fxcurrencyexchange.infrastructure.external.dto.OpenExchangeRatesResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException

@Component
class OpenExchangeRatesClient(
    private val restClient: RestClient,
    @Value("\${app.exchange.api-url}") private val apiUrl: String,
    @Value("\${app.exchange.app-id}") private val appId: String
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun fetchLatestRates(): OpenExchangeRatesResponse {
        log.info("Fetching latest exchange rates from OpenExchangeRates")
        return try {
            restClient.get()
                .uri("$apiUrl/latest.json?app_id=$appId")
                .retrieve()
                .body(OpenExchangeRatesResponse::class.java)
                ?: throw IllegalStateException("Empty response from OpenExchangeRates")
        } catch (ex: RestClientException) {
            log.error("Failed to fetch exchange rates: ${ex.message}")
            throw IllegalStateException("Could not fetch exchange rates from external API", ex)
        }
    }

    fun fetchSupportedCurrencies(): Map<String, String> {
        log.info("Fetching supported currencies from OpenExchangeRates")
        return try {
            restClient.get()
                .uri("$apiUrl/currencies.json?app_id=$appId")
                .retrieve()
                .body(object : ParameterizedTypeReference<Map<String, String>>() {})
                ?: emptyMap()
        } catch (ex: RestClientException) {
            log.error("Failed to fetch currencies: ${ex.message}")
            throw IllegalStateException("Could not fetch currencies from external API", ex)
        }
    }
}