package com.kvelasco.core

import com.kvelasco.core.api.ApiRequest

sealed class ResourceException(
    val error: Throwable? = null,
    override val message: String? = ""
) : Throwable(message = message, cause = error) {

    companion object {

        @JvmStatic
        fun invalidType(
            throwable: Throwable? = null,
            tag: String = "",
            type: String = ""
        ): ResourceException {
            return InvalidTypeException(
                error = throwable,
                message = "The resource stored with the tag " +
                    "${if (tag.isEmpty()) tag else "$tag "}is not a type of " +
                    "${if (type.isEmpty()) "unknown" else type}.",
                tag = tag,
                type = type
            )
        }

        @JvmStatic
        fun resourceNotFound(
            throwable: Throwable? = null,
            tag: String = "",
            dataSourceName: String = ""
        ): ResourceException {
            return ResourceNotFoundException(
                error = throwable,
                message = "Resource with the tag " +
                    "${if (tag.isEmpty()) tag else "$tag "}is not found" +
                    "${if (dataSourceName.isEmpty()) "" else " in $dataSourceName"}.",
                tag = tag,
                dataSourceName = dataSourceName
            )
        }

        @JvmStatic
        fun apiRequestError(
            request: ApiRequest,
            throwable: Throwable? = null,
            reason: String? = null,
            code: Int,
            responseBody: Any? = null
        ): ResourceException {
            if (code >= 500) {
                return ServerErrorException(
                    error = throwable,
                    message = (if (request.url.isEmpty()) "" else "${request.url} ") +
                        reason +
                        (if (!reason.isNullOrEmpty() && reason!!.endsWith(".")) "" else ".") +
                        (if (responseBody == null) "" else " Response body: $responseBody"),
                    code = code,
                    body = responseBody,
                    request = request
                )
            } else {
                // We treat everything below as ClientErrorException, even though
                // it is 200 or 300
                return ClientErrorException(
                    error = throwable,
                    message = (if (request.url.isEmpty()) "" else "${request.url} ") +
                        reason +
                        (if (!reason.isNullOrEmpty() && reason!!.endsWith(".")) "" else ".") +
                        (if (responseBody == null) "" else " Response body: $responseBody"),
                    code = code,
                    body = responseBody,
                    request = request
                )
            }
        }

        @JvmStatic
        fun networkError(request: ApiRequest? = null, throwable: Throwable? = null): ResourceException {
            return NetworkErrorException(
                error = throwable,
                message = throwable?.message ?: "Unable to connect to the server.",
                request = request
            )
        }

        @JvmStatic
        fun unexpectedError(
            throwable: Throwable? = null,
            message: String? = null,
            request: ApiRequest? = null
        ): ResourceException {
            return UnexpectedErrorException(
                error = throwable,
                message = message ?: throwable?.message,
                request = request
            )
        }
    }
}

class ClientErrorException internal constructor(
    error: Throwable? = null,
    message: String? = null,
    val code: Int = 400,
    val body: Any? = null,
    val request: ApiRequest
) : ResourceException(error = error, message = message)


class ServerErrorException internal constructor(
    error: Throwable? = null,
    message: String? = null,
    val code: Int = 500,
    val body: Any? = null,
    val request: ApiRequest
) : ResourceException(error = error, message = message)

class InvalidTypeException internal constructor(
    error: Throwable? = null,
    message: String? = null,
    val tag: String = "",
    val type: String = ""
) : ResourceException(error = error, message = message)

class ResourceNotFoundException internal constructor(
    error: Throwable? = null,
    message: String? = null,
    val tag: String = "",
    val dataSourceName: String = ""
) : ResourceException(error = error, message = message)

class NetworkErrorException internal constructor(
    error: Throwable? = null,
    message: String? = null,
    val request: ApiRequest? = null
) : ResourceException(error = error, message = message)

class UnexpectedErrorException internal constructor(
    error: Throwable? = null,
    message: String? = "",
    val request: ApiRequest? = null
) : ResourceException(error = error, message = message)