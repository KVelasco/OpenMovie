package com.kvelasco.core.ratelimiters

import java.util.concurrent.TimeUnit


class TimeRateLimiter(
    timeout: Int,
    timeUnit: TimeUnit
) : RateLimiter<Any> {

    private val timestamps = mutableMapOf<Any, Long>()
    private val timeout = timeUnit.toMillis(timeout.toLong())


    @Synchronized
    override fun shouldFetch(key: Any): Boolean {
        val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == null) {
            timestamps[key] = now
            return true
        }
        if (now - lastFetched > timeout) {
            timestamps[key] = now
            return true
        }
        return false
    }


    @Synchronized
    override fun reset(key: Any) {
        timestamps.remove(key)
    }

    private fun now() = System.currentTimeMillis()
}