package com.taskcanvas

import com.thoughtworks.gauge.BeforeScenario
import com.thoughtworks.gauge.BeforeSpec

class ExecutionHooks {
    @BeforeScenario
    fun setUp() {
        cleanUpDb()
    }

    private fun cleanUpDb() {
        TaskCanvasDb.truncateAll()
    }
}