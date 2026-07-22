package br.com.joaodddev.fxcurrencyexchange.domain.valueobject

data class CurrencyCode(val value: String) {
    init {
        require(value.length == 3) { "Currency code must be exactly 3 characters" }
        require(value == value.uppercase()) { "Currency code must be uppercase" }
    }

    override fun toString(): String = value
}