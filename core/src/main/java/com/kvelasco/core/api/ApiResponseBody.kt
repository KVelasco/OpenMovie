package com.kvelasco.core.api

abstract class ApiResponseBody<T>(
    val status: String? = null,
    val data: T? = null
)