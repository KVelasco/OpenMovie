package com.kvelasco.core.executors

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CurrentThreadExecutors(
    private val currentThread: Executor = CurrentThreadExecutor()
) : AbstractExecutorsProvider() {

    override fun mainThread() = currentThread
    override fun diskIO() = currentThread
    override fun computation() = currentThread
    override fun network() = currentThread
}