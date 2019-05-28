package com.kvelasco.openmovie.di

import android.content.Context
import com.kvelasco.openmovie.trending.movies.TrendingMoviesViewModel
import com.kvelasco.openmovie.trending.shows.TrendingShowsViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AssistedInjectModule::class,
    NetModule::class,
    RepositoryModule::class,
    RoomModule::class,
    ServiceModule::class,
    UseCasesModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    val trendingMoviesViewModel: TrendingMoviesViewModel
    val trendingShowsViewModel: TrendingShowsViewModel
}

@AssistedModule
@Module(includes = [AssistedInject_AssistedInjectModule::class])
interface AssistedInjectModule