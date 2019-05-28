package com.kvelasco.core.rxjava2

import com.kvelasco.core.api.ApiResponse
import io.reactivex.Flowable

interface NetworkBoundResource<ResultType, RequestType> {

    fun saveToLocal(data: RequestType)

    fun loadFromLocal(): Flowable<ResultType>

    fun createApiCall(): Flowable<ApiResponse<RequestType>>
}