package br.com.joaodddev.fxcurrencyexchange.infrastructure.persistence

import br.com.joaodddev.fxcurrencyexchange.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaUserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}