package com.kvelasco.core.retrofit

import com.kvelasco.core.api.ApiResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.plugins.RxJavaPlugins
import retrofit2.Response

internal class ApiResponseObservable<T>(private val upstream: Observable<Response<T>>) : Observable<ApiResponse<T>>() {

    override fun subscribeActual(observer: Observer<in ApiResponse<T>>) {
        upstream.subscribe(ApiResponseObserver(observer))
    }

    private class ApiResponseObserver<R> internal constructor(private val observer: Observer<in ApiResponse<R>>) : Observer<Response<R>> {

        override fun onSubscribe(disposable: Disposable) {
            observer.onSubscribe(disposable)
        }

        override fun onNext(response: Response<R>) {
            observer.onNext(response.toApiResponse())
        }

        override fun onError(throwable: Throwable) {
            try {
                observer.onNext(ApiResponse.error(throwable.toResourceException()))
            } catch (t: Throwable) {
                try {
                    observer.onError(t)
                } catch (inner: Throwable) {
                    Exceptions.throwIfFatal(inner)
                    RxJavaPlugins.onError(CompositeException(t, inner))
                }

                return
            }

            observer.onComplete()
        }

        override fun onComplete() {
            observer.onComplete()
        }
    }
}
