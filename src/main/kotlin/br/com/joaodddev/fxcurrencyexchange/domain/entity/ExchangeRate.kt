package br.com.joaodddev.fxcurrencyexchange.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(
    name = "exchange_rates",
    uniqueConstraints = [UniqueConstraint(columnNames = ["from_currency", "to_currency"])]
)
class ExchangeRate(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "from_currency", nullable = false, length = 3)
    val fromCurrency: String,

    @Column(name = "to_currency", nullable = false, length = 3)
    val toCurrency: String,

    @Column(nullable = false, precision = 18, scale = 8)
    val rate: BigDecimal,

    @Column(nullable = false)
    val fetchedAt: LocalDateTime = LocalDateTime.now()
) {
    fun isStale(thresholdMinutes: Long): Boolean =
        fetchedAt.isBefore(LocalDateTime.now().minusMinutes(thresholdMinutes))

    fun convert(amount: BigDecimal): BigDecimal =
        amount.multiply(rate).setScale(2, java.math.RoundingMode.HALF_UP)

    override fun toString(): String =
        "ExchangeRate($fromCurrency -> $toCurrency = $rate)"
}