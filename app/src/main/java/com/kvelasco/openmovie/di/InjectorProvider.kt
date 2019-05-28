package com.kvelasco.openmovie.di

import android.app.Activity

interface InjectorProvider {
    val component: AppComponent
}

val Activity.injector get() = (application as InjectorProvider).component