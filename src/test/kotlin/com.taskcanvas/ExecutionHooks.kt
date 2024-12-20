package com.taskcanvas

import com.thoughtworks.gauge.BeforeSpec
import com.thoughtworks.gauge.ExecutionContext
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class ExecutionHooks {
    @BeforeSpec
    fun setUp(executionContext: ExecutionContext) {
        cleanUpDb()
        setUpData(executionContext)
    }

    private fun cleanUpDb() {
        Database.truncateAll()
    }

    private fun setUpData(executionContext: ExecutionContext) {
        val specFilePath = executionContext.currentSpecification.fileName
        val projectDir = System.getProperty("user.dir")
        val relativePath = specFilePath.removePrefix("$projectDir/")
        val specDirectory = relativePath.substringBeforeLast(".")
        val removedSpecDirectory = specDirectory.removePrefix("specs/")
        val fixturesPath = "${projectDir}/fixtures/$removedSpecDirectory/task_canvas"

        val tableOrderingFile = File("$fixturesPath/table_ordering.txt")
        if (tableOrderingFile.exists()) {
            val sqlFiles = tableOrderingFile.readLines()

            val connection =  Database.connection()
            sqlFiles.forEach { sqlFileName ->
                val sqlFilePath = Paths.get("$fixturesPath/$sqlFileName.sql")
                if (Files.exists(sqlFilePath)) {
                    val sql = Files.readString(sqlFilePath)
                    connection.createStatement().use { stmt ->
                        stmt.execute(sql)
                    }
                }
            }
        }
    }
}