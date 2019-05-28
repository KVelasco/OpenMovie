package com.kvelasco.core.rxjava2

import com.kvelasco.core.api.Resource
import com.kvelasco.core.executors.DragonExecutors
import io.reactivex.Flowable


abstract class AbstractMutateResource<ResultType, RequestType> : AbstractNetworkBoundResource<ResultType, RequestType>() {

    protected abstract fun mutate()

    override fun execute(): Flowable<Resource<ResultType>> {
        return Flowable.fromCallable { mutate() }
            .flatMap {
                loadFromLocal()
            }
            .subscribeOn(DragonExecutors.diskIO().toScheduler())
            .flatMap { data ->
                if (shouldFetch(data)) {
                    fetchFromNetwork(data)
                } else {
                    Flowable.just(Resource.success(data))
                }
            }
            .observeOn(DragonExecutors.mainThread().toScheduler())
            .startWith(Resource.loading())
    }
}