package br.com.joaodddev.fxcurrencyexchange.infrastructure.cache

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

@Service
class ExchangeRateCacheService(
    private val redisTemplate: RedisTemplate<String, Any>,
    @Value("\${app.exchange.cache-ttl-minutes}") private val cacheTtlMinutes: Long
) {

    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        private const val RATE_KEY_PREFIX = "fx:rate:"
        private const val ALL_RATES_KEY = "fx:rates:all"
    }

    fun cacheRate(from: String, to: String, rate: BigDecimal) {
        val key = buildKey(from, to)
        redisTemplate.opsForValue().set(key, rate.toString(), cacheTtlMinutes, TimeUnit.MINUTES)
        log.debug("Cached rate $from->$to = $rate (TTL: ${cacheTtlMinutes}min)")
    }

    fun getCachedRate(from: String, to: String): BigDecimal? {
        val key = buildKey(from, to)
        val value = redisTemplate.opsForValue().get(key) ?: return null
        return runCatching { BigDecimal(value.toString()) }.getOrNull()
    }

    fun cacheAllRates(rates: Map<String, BigDecimal>) {
        val stringRates = rates.mapValues { it.value.toString() }
        redisTemplate.opsForHash<String, String>().putAll(ALL_RATES_KEY, stringRates)
        redisTemplate.expire(ALL_RATES_KEY, cacheTtlMinutes, TimeUnit.MINUTES)
        log.info("Cached ${rates.size} rates in Redis (TTL: ${cacheTtlMinutes}min)")
    }

    fun getAllCachedRates(): Map<String, BigDecimal>? {
        val entries = redisTemplate.opsForHash<String, String>().entries(ALL_RATES_KEY)
        if (entries.isEmpty()) return null
        return entries.mapValues { BigDecimal(it.value) }
    }

    fun isCacheValid(from: String, to: String): Boolean {
        val key = buildKey(from, to)
        return redisTemplate.hasKey(key) == true
    }

    fun evictRate(from: String, to: String) {
        val key = buildKey(from, to)
        redisTemplate.delete(key)
        log.debug("Evicted cache for $from->$to")
    }

    fun evictAllRates() {
        redisTemplate.delete(ALL_RATES_KEY)
        log.info("Evicted all rates from cache")
    }

    private fun buildKey(from: String, to: String): String =
        "$RATE_KEY_PREFIX${from}_$to"
}