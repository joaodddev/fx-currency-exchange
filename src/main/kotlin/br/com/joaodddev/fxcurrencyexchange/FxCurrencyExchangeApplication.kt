package br.com.joaodddev.fxcurrencyexchange

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FxCurrencyExchangeApplication

fun main(args: Array<String>) {
	runApplication<FxCurrencyExchangeApplication>(*args)
}
