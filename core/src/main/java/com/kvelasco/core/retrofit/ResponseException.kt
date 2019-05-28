package com.kvelasco.core.retrofit

import com.kvelasco.core.api.ApiRequest


internal class ResponseException(
    override val cause: Throwable,
    override val message: String? = null,
    val request: ApiRequest
) : Throwable(message = message, cause = cause)