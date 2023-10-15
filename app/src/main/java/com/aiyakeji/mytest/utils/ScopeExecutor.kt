package com.aiyakeji.mytest.utils

import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author CWQ
 * @date 2023/10/15
 */
class ScopeExecutor(private val executor: Executor) : Executor {

    private val shutdown = AtomicBoolean()

    override fun execute(command: Runnable?) {
        if (shutdown.get()) {
            return
        }
        executor.execute {
            if (shutdown.get()) {
                return@execute
            }
            command?.run()
        }
    }

    fun shutdownn() {
        shutdown.set(true)
    }
}