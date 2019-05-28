package com.kvelasco.core.executors

import java.util.concurrent.Executor

class DragonExecutors private constructor(
    private val executorsProvider: ExecutorsProvider
) {
    companion object {
        private lateinit var INSTANCE: DragonExecutors

        @JvmStatic
        fun init(executorsProvider: ExecutorsProvider) {
            INSTANCE = DragonExecutors(executorsProvider)
        }

        @JvmStatic
        fun diskIO(): Executor {
            return INSTANCE.executorsProvider.diskIO()
        }

        @JvmStatic
        fun mainThread(): Executor {
            return INSTANCE.executorsProvider.mainThread()
        }

        @JvmStatic
        fun computation(): Executor {
            return INSTANCE.executorsProvider.computation()
        }

        @JvmStatic
        fun network(): Executor {
            return INSTANCE.executorsProvider.network()
        }
    }
}