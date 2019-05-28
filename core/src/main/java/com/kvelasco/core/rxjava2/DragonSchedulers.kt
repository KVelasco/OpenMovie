package com.kvelasco.core.rxjava2

import com.kvelasco.core.executors.DragonExecutors
import com.kvelasco.core.executors.DragonExecutors.Companion.computation
import com.kvelasco.core.executors.DragonExecutors.Companion.diskIO
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


object DragonSchedulers {

    fun io() = Schedulers.io()

    fun computation() = Schedulers.computation()

    fun network() = Schedulers.io()

    fun mainThread() = DragonExecutors.mainThread().toScheduler()
}