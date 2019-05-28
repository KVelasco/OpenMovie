package com.kvelasco.core.executors

import java.util.concurrent.Executor

class CurrentThreadExecutor : Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}