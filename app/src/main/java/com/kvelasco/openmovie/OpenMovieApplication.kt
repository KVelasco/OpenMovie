package com.kvelasco.openmovie

import android.app.Application
import com.kvelasco.core.executors.DragonExecutors
import com.kvelasco.openmovie.di.DaggerAppComponent
import com.kvelasco.openmovie.di.InjectorProvider
import com.kvelasco.openmovie.net.AndroidExecutors

class OpenMovieApplication : Application(), InjectorProvider {

    override fun onCreate() {
        super.onCreate()
        DragonExecutors.init(AndroidExecutors())
        DragonExecutors.diskIO()
    }

    override val component by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}