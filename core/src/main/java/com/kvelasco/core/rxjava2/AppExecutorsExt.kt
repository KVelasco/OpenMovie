package com.kvelasco.core.rxjava2

import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

fun Executor.toScheduler() = Schedulers.from(this)