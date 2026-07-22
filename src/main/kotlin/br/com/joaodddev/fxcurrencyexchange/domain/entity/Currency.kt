package br.com.joaodddev.fxcurrencyexchange.domain.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "currencies")
class Currency(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 3)
    val code: String,

    @Column(nullable = false, length = 100)
    val name: String,

    @Column(length = 10)
    val symbol: String? = null,

    @Column(nullable = false)
    val active: Boolean = true,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun isSupported(): Boolean = active

    override fun toString(): String = "Currency(code=$code, name=$name)"
}