package com.kvelasco.openmovie.net

import android.os.Handler
import android.os.Looper
import com.kvelasco.core.executors.AbstractExecutorsProvider
import java.util.concurrent.Executor

class AndroidExecutors : AbstractExecutorsProvider() {
    private val mainThreadExecutor = MainThreadExecutor()

    override fun mainThread(): Executor = mainThreadExecutor

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}