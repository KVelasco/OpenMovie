package com.kvelasco.core.retrofit


import com.kvelasco.core.ClientErrorException
import com.kvelasco.core.InvalidTypeException
import com.kvelasco.core.ResourceException
import com.kvelasco.core.ResourceNotFoundException
import com.kvelasco.core.ServerErrorException
import com.kvelasco.core.UnexpectedErrorException
import com.kvelasco.core.api.ApiRequest
import com.kvelasco.core.api.ApiRequestBody
import com.kvelasco.core.api.ApiResponse
import com.kvelasco.core.api.Headers
import okhttp3.Request
import okio.Buffer
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> Response<T>.toApiResponse(): ApiResponse<T> {
    if (isSuccessful) {
        return ApiResponse.success(
            headers = headers().toApiHeaders(),
            code = code(),
            message = message(),
            body = body(),
            request = raw().request().toApiRequest()
        )
    } else {
        val errorMessage: String
        errorMessage = try {
            val message = if (errorBody() == null) null else errorBody()!!.string()
            if (message == null || message.isEmpty()) {
                message()
            } else {
                message
            }
        } catch (e: IOException) {
            e.printStackTrace()
            "Api response with " + code() + " status code and the " +
                "response body is too large to fit into the memory."
        }

        return ApiResponse.error(
            headers = headers().toApiHeaders(),
            code = code(),
            message = message(),
            request = raw().request().toApiRequest(),
            error = IOException(errorMessage)
        )
    }
}

fun okhttp3.Headers.toApiHeaders(): Headers {
    val builder = Headers.builder()
    for (i in 0 until size()) {
        builder.addLenient(name(i), value(i))
    }
    return builder.build()
}

fun Request.toApiRequest(): ApiRequest {
    val copy = newBuilder().build()
    val buffer = Buffer()

    try {
        copy.body()?.writeTo(buffer)
    } catch (e: Exception) {
        // Do nothing, just don't copy over the body
    }

    return ApiRequest(
        url = copy.url().toString(),
        method = copy.method(),
        headers = copy.headers().toApiHeaders(),
        body = ApiRequestBody(
            contentLength = copy.body()?.contentLength() ?: 0L,
            contentType = copy.body()?.contentType()?.type() ?: "text/plain",
            body = buffer.readUtf8()
        )
    )
}

fun Throwable.toResourceException(message: String? = null): ResourceException {
    val request: ApiRequest?
    val cause: Throwable
    if (this is ResponseException) {
        cause = this.cause
        request = this.request
    } else {
        cause = this
        request = null
    }

    return when (cause) {
        is ClientErrorException -> return cause
        is ServerErrorException -> return cause
        is ResourceNotFoundException -> return cause
        is InvalidTypeException -> return cause
        is UnexpectedErrorException -> return cause
        is HttpException -> {
            val response = cause.response()
            return ResourceException.apiRequestError(
                request = response.raw().request().toApiRequest(),
                throwable = this,
                reason = cause.message(),
                code = cause.code(),
                responseBody = response.body()
            )
        }
        is IOException -> ResourceException.networkError(request, cause)
        else -> ResourceException.unexpectedError(cause, message, request)
    }
}