package com.kvelasco.core.executors

import java.util.concurrent.Executor

interface ExecutorsProvider {

    fun diskIO(): Executor

    fun mainThread(): Executor

    fun computation(): Executor

    fun network(): Executor
}