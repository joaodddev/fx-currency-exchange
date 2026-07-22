package br.com.joaodddev.fxcurrencyexchange.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "conversion_history")
class ConversionHistory(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long? = null,

    @Column(name = "from_currency", nullable = false, length = 3)
    val fromCurrency: String,

    @Column(name = "to_currency", nullable = false, length = 3)
    val toCurrency: String,

    @Column(nullable = false, precision = 18, scale = 2)
    val amount: BigDecimal,

    @Column(name = "converted_amount", nullable = false, precision = 18, scale = 2)
    val convertedAmount: BigDecimal,

    @Column(nullable = false, precision = 18, scale = 8)
    val rate: BigDecimal,

    @Column(nullable = false)
    val convertedAt: LocalDateTime = LocalDateTime.now()
)