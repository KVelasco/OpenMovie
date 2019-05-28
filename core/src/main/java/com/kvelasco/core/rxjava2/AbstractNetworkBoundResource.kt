package com.kvelasco.core.rxjava2

import com.kvelasco.core.ResourceException
import com.kvelasco.core.api.*
import com.kvelasco.core.executors.DragonExecutors
import io.reactivex.Flowable
import java.io.IOException

abstract class AbstractNetworkBoundResource<ResultType, RequestType> : NetworkBoundResource<ResultType, RequestType> {

    private var result: Flowable<Resource<ResultType>> = Flowable.empty()
    private var executed = false

    fun toFlowable() = executeWorkflow()

    fun toSingle() = executeWorkflow()
        .filter { it.status != Status.LOADING }
        .singleOrError()

    protected fun fetchFromNetwork(localData: ResultType): Flowable<Resource<ResultType>> {
        val apiResponse = createApiCall()

        return apiResponse
            .onErrorReturn {
                ApiResponse.error(it)
            }
            .flatMap { response ->
                when (response) {
                    is ApiSuccessResponse -> {

                        saveToLocal(processResponse(response))
                        loadFromLocal()
                            .observeOn(DragonExecutors.mainThread().toScheduler())
                            .flatMap { data ->
                                Flowable.just(Resource.success(data))
                            }
                    }
                    is ApiEmptyResponse -> {

                        loadFromLocal()
                            .observeOn(DragonExecutors.mainThread().toScheduler())
                            .flatMap { data ->
                                Flowable.just(Resource.success(data))
                            }
                    }
                    is ApiErrorResponse -> {

                        onFetchFailed(response)
                        Flowable.just(Resource.error(response.error, localData))
                    }
                    else -> {
                        // Not going to happen
                        Flowable.just(Resource.error(
                            ResourceException.unexpectedError(
                            throwable = IOException("Unknown api response.")
                        )))
                    }
                }
            }
            .observeOn(DragonExecutors.mainThread().toScheduler())
            .startWith(Resource.loading(localData))
    }


    protected open fun onFetchFailed(response: ApiErrorResponse<RequestType>) {}

    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected abstract fun execute(): Flowable<Resource<ResultType>>

    private fun executeWorkflow(): Flowable<Resource<ResultType>> {
        if (!executed) {
            executed = true

            result = execute()
        }
        return result
    }
}