package com.kvelasco.core.retrofit

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response

internal class CallExecuteObservable<T>(private val originalCall: Call<T>) : Observable<Response<T>>() {

    override fun subscribeActual(observer: Observer<in Response<T>>) {
        // Since Call is a one-shot type, clone it for each new observer.
        val call = originalCall.clone()
        val disposable = CallDisposable(call)
        observer.onSubscribe(disposable)

        var terminated = false
        try {
            val response = call.execute()
            if (!disposable.isDisposed) {
                observer.onNext(response)
            }
            if (!disposable.isDisposed) {
                terminated = true
                observer.onComplete()
            }
        } catch (t: Throwable) {
            val throwable = t.toResponseException(call.request())
            Exceptions.throwIfFatal(throwable)
            if (terminated) {
                RxJavaPlugins.onError(throwable)
            } else if (!disposable.isDisposed) {
                try {
                    observer.onError(throwable)
                } catch (inner: Throwable) {
                    Exceptions.throwIfFatal(inner)
                    RxJavaPlugins.onError(CompositeException(throwable, inner))
                }
            }
        }
    }

    private class CallDisposable internal constructor(private val call: Call<*>) : Disposable {
        @Volatile
        private var disposed: Boolean = false

        override fun dispose() {
            disposed = true
            call.cancel()
        }

        override fun isDisposed(): Boolean {
            return disposed
        }
    }
}

private fun Throwable.toResponseException(request: Request): Throwable {
    return ResponseException(
        cause = this,
        message = message,
        request = request.toApiRequest()
    )
}