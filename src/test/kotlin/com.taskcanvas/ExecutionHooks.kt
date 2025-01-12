package com.taskcanvas

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.standalone.MappingsLoader
import com.thoughtworks.gauge.AfterSpec
import com.thoughtworks.gauge.BeforeSpec
import com.thoughtworks.gauge.ExecutionContext
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class ExecutionHooks {
    @BeforeSpec
    fun setUp(executionContext: ExecutionContext) {
        cleanUpDb()
        resetAllMocks()

        setUpMocks(executionContext)
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

            val connection = Database.connection()
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

    private fun resetAllMocks() {
        TaskCanvasApiForWeb.resetToDefaultMappings()
    }

    private fun setUpMocks(executionContext: ExecutionContext) {
        val specResourcePath = executionContext.getSpecResourcePath()
        val mockDirPath = "$specResourcePath/mappings"
        val mockDir = File(mockDirPath)
        if (mockDir.exists() && mockDir.isDirectory) {
            mockDir.listFiles { file -> file.extension == "json" }?.forEach { jsonFile ->
                TaskCanvasApiForWeb.loadMapping(jsonFile)
            }
        }
    }

    private fun ExecutionContext.getSpecResourcePath(): String {
        val fileName = this.currentSpecification.fileName
        val specDir = fileName.replace(System.getProperty("user.dir") + "/", "")
            .split("/").drop(1).dropLast(1).joinToString("/")

        return "$specDir/${File(fileName).nameWithoutExtension}"
    }
}