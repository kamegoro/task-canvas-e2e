package com.taskcanvas

import com.codeborne.selenide.Configuration
import com.codeborne.selenide.WebDriverRunner
import com.thoughtworks.gauge.AfterSpec
import com.thoughtworks.gauge.BeforeScenario
import com.thoughtworks.gauge.ExecutionContext
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.logging.LogType
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class ExecutionHooks {
    @BeforeScenario
    fun setUp(executionContext: ExecutionContext) {
        if (isTaskCanvasApi(executionContext)) {
            cleanUpDb()
            setUpData(executionContext)
        }

        if (isTaskCanvasWeb(executionContext)) {
            WebDriverRunner.closeWebDriver()
            prepareSelenide()

            resetTaskCanvasApiMock()
            setUpMocks(executionContext)
        }
    }

    @AfterSpec
    fun logDevToolsOnFailure(executionContext: ExecutionContext) {
        if (isTaskCanvasWeb(executionContext) && executionContext.currentSpecification.isFailing) {
            println("ステップ失敗のため、ネットワークとブラウザログを出力します")
            val driver = WebDriverRunner.getWebDriver()
            try {
                val performanceLog = driver.manage().logs().get(LogType.PERFORMANCE)
                println("=== ネットワークログ ===")
                performanceLog.forEach {
                    println(it.message)
                }
            } catch (e: Exception) {
                println("ネットワークログの取得に失敗しました: ${e.message}")
                e.printStackTrace()
            }

            try {
                val browserLog = driver.manage().logs().get(LogType.BROWSER)
                println("=== ブラウザログ ===")
                browserLog.forEach {
                    println(it.message)
                }
            } catch (e: Exception) {
                println("ブラウザログの取得に失敗しました: ${e.message}")
            }
        }
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

    private fun resetTaskCanvasApiMock() {
        TaskCanvasApiForWeb.resetToDefaultMappings()
    }

    private fun setUpMocks(executionContext: ExecutionContext) {
        mapOf(
            TaskCanvasApiForWeb to "task_canvas_api"
        ).forEach { (mock, dir) ->
            val mappingsPath = executionContext.getSpecResourcePath()
            val mappingsFile = Paths.get("fixtures//$mappingsPath/$dir")
            if (Files.exists(mappingsFile)) {
                mock.loadMappingsFrom(mappingsFile.toFile())
            }
        }
    }

    private fun ExecutionContext.getSpecResourcePath(): String {
        val fileName = this.currentSpecification.fileName
        val specDir = fileName.replace(System.getProperty("user.dir") + "/", "")
            .split("/").drop(1).dropLast(1).joinToString("/")

        return "$specDir/${File(fileName).nameWithoutExtension}"
    }

    private fun isTaskCanvasApi(executionContext: ExecutionContext): Boolean {
        val specFilePath = executionContext.currentSpecification.fileName

        return specFilePath.contains("specs/task-canvas/")
    }

    private fun isTaskCanvasWeb(executionContext: ExecutionContext): Boolean {
        val specFilePath = executionContext.currentSpecification.fileName

        return specFilePath.contains("specs/task-canvas-web/")
    }

    private fun prepareSelenide() {
        with(config.selenide) {
            Configuration.baseUrl = baseUrl
            Configuration.headless = headless
            Configuration.remote = remote
            Configuration.browser = browser
            Configuration.reopenBrowserOnFail = reopenBrowserOnFail
            Configuration.holdBrowserOpen = holdBrowserOpen
            Configuration.browserCapabilities = ChromeOptions().apply {
                addArguments("--incognito")
                setCapability("goog:loggingPrefs", mapOf("performance" to "ALL"))
            }
            Configuration.timeout = timeout
        }
    }
}