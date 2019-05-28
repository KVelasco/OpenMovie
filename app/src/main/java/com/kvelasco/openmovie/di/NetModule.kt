package com.kvelasco.openmovie.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kvelasco.core.retrofit.RxJavaCallAdapterFactory
import com.kvelasco.openmovie.BuildConfig
import com.kvelasco.openmovie.net.AuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
object NetModule  {

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_ENDPOINT)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesOkhttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideCustomGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun providesInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }
}