package com.kvelasco.core.api

data class Resource<out T> internal constructor(
    val status: Status,
    val data: T? = null,
    val error: Throwable? = null
) {

    fun <R> clone(data: R? = null): Resource<R> {
        return Resource(
            status,
            data,
            error
        )
    }

    companion object {

        @JvmStatic
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        @JvmStatic
        fun <T> error(error: Throwable? = null, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        @JvmStatic
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data)
        }
    }
}