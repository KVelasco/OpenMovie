package com.kvelasco.core.api

data class ApiRequest(
    val url: String,
    val method: String,
    val headers: Headers = Headers.builder().build(),
    val body: ApiRequestBody = ApiRequestBody()
)


val EMPTY_REQUEST = ApiRequest("", "")