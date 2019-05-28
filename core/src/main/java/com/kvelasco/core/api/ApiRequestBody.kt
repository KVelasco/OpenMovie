package com.kvelasco.core.api

data class ApiRequestBody(
    val contentLength: Long = 0,
    val contentType: String = "text/plain",
    val body: String? = null
)