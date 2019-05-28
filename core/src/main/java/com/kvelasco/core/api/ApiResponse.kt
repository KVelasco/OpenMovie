@file:Suppress("UNUSED_PARAMETER")

package com.kvelasco.core.api

import com.kvelasco.core.ClientErrorException
import com.kvelasco.core.NetworkErrorException
import com.kvelasco.core.ServerErrorException
import com.kvelasco.core.UnexpectedErrorException

@Suppress("unused") // T is used in extending classes
open class ApiResponse<T> internal constructor(
    open val headers: Headers? = null,
    open val code: Int = 0,
    open val message: String = "",
    open val body: T? = null,
    open val request: ApiRequest
) {

    fun isSuccessful(): Boolean {
        return code in 200..299
    }

    fun code(): Int {
        return code
    }

    fun headers(): Headers? {
        return headers
    }

    fun message(): String {
        return message
    }

    fun body(): T? {
        return body
    }

    fun <R> copy(body: R? = null): ApiResponse<R> {
        return when (this) {
            is ApiSuccessResponse, is ApiEmptyResponse -> {
                success(
                    headers = headers,
                    code = code,
                    message = message,
                    body = body,
                    request = request
                )
            }
            else -> {
                this as ApiErrorResponse
                error(
                    headers = headers,
                    code = code,
                    message = message,
                    request = request,
                    error = error
                )
            }
        }
    }

    companion object {

        @JvmStatic
        fun <T> success(response: ApiResponse<T>): ApiResponse<T> {
            return success(
                headers = response.headers,
                code = 200,
                message = response.message,
                body = response.body,
                request = response.request
            )
        }

        @JvmStatic
        fun <T> success(
            headers: Headers? = null,
            code: Int = 200,
            message: String = "OK",
            body: T? = null,
            request: ApiRequest = EMPTY_REQUEST
        ): ApiResponse<T> {
            return if (body == null) {
                ApiEmptyResponse(
                    headers = headers,
                    code = code,
                    message = message,
                    request = request
                )
            } else {
                ApiSuccessResponse(
                    headers = headers,
                    code = code,
                    message = message,
                    body = body,
                    request = request
                )
            }
        }

        @JvmStatic
        fun <T> error(error: Throwable): ApiResponse<T> {
            return error(
                headers = null,
                error = error,
                request = when (error) {
                    is ClientErrorException -> error.request
                    is ServerErrorException -> error.request
                    is NetworkErrorException -> error.request ?: EMPTY_REQUEST
                    is UnexpectedErrorException -> error.request ?: EMPTY_REQUEST
                    else -> EMPTY_REQUEST
                }
            )
        }

        @JvmStatic
        fun <T> error(error: Throwable, response: ApiResponse<*>): ApiResponse<T> {
            return ApiErrorResponse(
                headers = response.headers,
                code = response.code,
                message = response.message,
                request = response.request,
                error = error
            )
        }

        @JvmStatic
        fun <T> error(
            headers: Headers? = null,
            code: Int = 400,
            message: String = "Bad Request",
            request: ApiRequest,
            error: Throwable
        ): ApiResponse<T> {
            return ApiErrorResponse(
                headers = headers,
                code = code,
                message = message,
                request = request,
                error = error
            )
        }
    }
}

class ApiEmptyResponse<T> internal constructor(
    override val headers: Headers? = null,
    override val code: Int = 0,
    override val message: String = "",
    override val request: ApiRequest
) : ApiResponse<T>(
    headers = headers,
    code = code,
    message = message,
    request = request
)

class ApiSuccessResponse<T> internal constructor(
    override val headers: Headers? = null,
    override val code: Int = 0,
    override val message: String = "",
    override val body: T,
    override val request: ApiRequest
) : ApiResponse<T>(
    headers = headers,
    code = code,
    message = message,
    body = body,
    request = request
)

class ApiErrorResponse<T> internal constructor(
    override val headers: Headers? = null,
    override val code: Int = 0,
    override val message: String = "",
    override val request: ApiRequest,
    val error: Throwable
) : ApiResponse<T>(
    headers = headers,
    code = code,
    message = message,
    request = request
)