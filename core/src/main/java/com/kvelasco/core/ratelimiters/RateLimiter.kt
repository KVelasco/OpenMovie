package com.kvelasco.core.ratelimiters


interface RateLimiter<in KEY> {

    fun shouldFetch(key: KEY): Boolean

    fun reset(key: KEY)
}