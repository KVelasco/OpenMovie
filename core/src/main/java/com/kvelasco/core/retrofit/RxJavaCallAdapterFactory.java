package com.kvelasco.core.retrofit;


import com.kvelasco.core.api.ApiResponse;
import org.reactivestreams.Publisher;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


@SuppressWarnings("ALL")
public class RxJavaCallAdapterFactory extends CallAdapter.Factory {

    private RxJava2CallAdapterFactory original;
    private boolean enforceApiResponseOnly;

    private RxJavaCallAdapterFactory(boolean enforceApiResponseOnly) {
        this.original = RxJava2CallAdapterFactory.create();
        this.enforceApiResponseOnly = enforceApiResponseOnly;
    }

    public static RxJavaCallAdapterFactory create() {
        return create(false);
    }


    public static RxJavaCallAdapterFactory create(boolean enforceApiResponseOnly) {
        return new RxJavaCallAdapterFactory(enforceApiResponseOnly);
    }

    @Override
    public CallAdapter get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);

        if (rawType == Completable.class) {
            // Completable is not parameterized (which is what the rest of this method deals with) so it
            // can only be created with a single configuration.
            return new RxJavaCallAdapter(Void.class, false, false, true, false, false,
                false, true, original.get(returnType, annotations, retrofit));
        }

        boolean isFlowable = rawType == Flowable.class;
        boolean isSingle = rawType == Single.class;
        boolean isMaybe = rawType == Maybe.class;
        if (rawType != Observable.class && !isFlowable && !isSingle && !isMaybe) {
            return null;
        }

        boolean isApiResponse = false;
        boolean isResult = false;
        boolean isBody = false;
        String name = isFlowable ? "Flowable"
            : isSingle ? "Single"
            : isMaybe ? "Maybe" : "Observable";

        Type responseType;
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(name + " return type must be parameterized"
                + " as " + name + "<Foo> or " + name + "<? extends Foo>");
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);

        if (rawObservableType == ApiResponse.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("ApiResponse must be parameterized"
                    + "as ApiResponse<Foo> or ApiResponse<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
            isApiResponse = true;
        } else if (enforceApiResponseOnly) {
            throw new IllegalStateException(name + " return type must be parameterized"
                + " as " + name + "<ApiResponse<Foo>> or " + name + "<ApiResponse<? extends Foo>>");
        } else if (rawObservableType == Response.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Response must be parameterized"
                    + " as Response<Foo> or Response<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
        } else if (rawObservableType == Result.class) {
            if (!(observableType instanceof ParameterizedType)) {
                throw new IllegalStateException("Result must be parameterized"
                    + " as Result<Foo> or Result<? extends Foo>");
            }
            responseType = getParameterUpperBound(0, (ParameterizedType) observableType);
            isResult = true;
        } else {
            responseType = observableType;
            isBody = true;
        }

        return new RxJavaCallAdapter(responseType, isApiResponse, isResult, isBody, isFlowable,
            isSingle, isMaybe, false, original.get(returnType, annotations, retrofit));
    }

    private static class RxJavaCallAdapter<R> implements CallAdapter<R, Object> {
        private final Type responseType;
        private final boolean isApiResponse;
        private final boolean isResult;
        private final boolean isBody;
        private final boolean isFlowable;
        private final boolean isSingle;
        private final boolean isMaybe;
        private final boolean isCompletable;
        private final CallAdapter<R, Object> callAdapter;

        RxJavaCallAdapter(Type responseType,
                          boolean isApiResponse,
                          boolean isResult,
                          boolean isBody,
                          boolean isFlowable,
                          boolean isSingle,
                          boolean isMaybe,
                          boolean isCompletable,
                          CallAdapter<R, Object> callAdapter) {
            this.responseType = responseType;
            this.isApiResponse = isApiResponse;
            this.isResult = isResult;
            this.isBody = isBody;
            this.isFlowable = isFlowable;
            this.isSingle = isSingle;
            this.isMaybe = isMaybe;
            this.isCompletable = isCompletable;
            this.callAdapter = callAdapter;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public Object adapt(Call<R> call) {
            Object observable = isApiResponse ? adaptApiResponse(call) : callAdapter.adapt(call);

            if (isFlowable) {
                return ((Flowable) observable).onErrorResumeNext(new Function<Throwable, Publisher>() {
                    @Override
                    public Publisher apply(Throwable throwable) throws Exception {
                        return Flowable.error(ApiExtensionKt.toResourceException(throwable, (String) null));
                    }
                });
            }
            if (isSingle) {
                return ((Single) observable).onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource apply(Throwable throwable) throws Exception {
                        return Single.error(ApiExtensionKt.toResourceException(throwable, (String) null));
                    }
                });
            }
            if (isMaybe) {
                return ((Maybe) observable).onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    @Override
                    public MaybeSource apply(Throwable throwable) throws Exception {
                        return Maybe.error(ApiExtensionKt.toResourceException(throwable, (String) null));
                    }
                });
            }
            if (isCompletable) {
                return ((Completable) observable).onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Throwable throwable) throws Exception {
                        return Completable.error(ApiExtensionKt.toResourceException(throwable, (String) null));
                    }
                });
            }
            if (observable instanceof Observable) {
                return ((Observable) observable).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        return Observable.error(ApiExtensionKt.toResourceException(throwable, (String) null));
                    }
                });
            }

            return observable;
        }

        private Object adaptApiResponse(Call<R> call) {
            Observable<Response<R>> responseObservable = new CallExecuteObservable<>(call);

            Observable<?> observable;
            if (isApiResponse) {
                observable = new ApiResponseObservable<>(responseObservable);
            } else {
                observable = responseObservable;
            }

            if (isFlowable) {
                return observable.toFlowable(BackpressureStrategy.LATEST);
            }
            if (isSingle) {
                return observable.singleOrError();
            }
            if (isMaybe) {
                return observable.singleElement();
            }
            if (isCompletable) {
                return observable.ignoreElements();
            }
            return observable;
        }
    }
}
