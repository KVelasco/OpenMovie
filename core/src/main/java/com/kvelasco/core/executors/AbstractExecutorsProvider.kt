package com.kvelasco.core.executors

import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class AbstractExecutorsProvider : ExecutorsProvider {

    private val defaultDiskIO: Executor = Executors.newSingleThreadExecutor()
    private val defaultNetwork: Executor = Executors.newFixedThreadPool(3)
    private val defaultComputation: Executor = Executors.newFixedThreadPool(3)

    protected open fun getDiskIoExecutor() = defaultDiskIO
    protected open fun getNetworkExecutor() = defaultNetwork
    protected open fun getComputationExecutor() = defaultComputation

    override fun diskIO() = getDiskIoExecutor()

    override fun network() = getNetworkExecutor()

    override fun computation() = getComputationExecutor()
}