package br.com.joaodddev.fxcurrencyexchange.infrastructure.scheduler

import br.com.joaodddev.fxcurrencyexchange.application.usecase.FetchExchangeRatesUseCase
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ExchangeRateScheduler(
    private val fetchExchangeRatesUseCase: FetchExchangeRatesUseCase
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedRateString = "\${app.exchange.scheduler-interval-ms:1800000}")
    fun refreshRates() {
        log.info("Scheduler triggered: refreshing exchange rates")
        runCatching {
            val count = fetchExchangeRatesUseCase.execute()
            log.info("Scheduler completed: $count rates updated")
        }.onFailure {
            log.error("Scheduler failed to refresh rates: ${it.message}", it)
        }
    }

    @Scheduled(cron = "0 0 6 * * *")
    fun dailyFullRefresh() {
        log.info("Daily full refresh triggered at 06:00")
        runCatching {
            val count = fetchExchangeRatesUseCase.execute()
            log.info("Daily full refresh completed: $count rates updated")
        }.onFailure {
            log.error("Daily full refresh failed: ${it.message}", it)
        }
    }
}