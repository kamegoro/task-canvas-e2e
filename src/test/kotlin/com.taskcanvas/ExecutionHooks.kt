package com.taskcanvas

import com.thoughtworks.gauge.BeforeScenario

class ExecutionHooks {
    @BeforeScenario
    fun setUp() {
        cleanUpDb()
    }

    private fun cleanUpDb() {
        Database.truncateAll()
    }
}