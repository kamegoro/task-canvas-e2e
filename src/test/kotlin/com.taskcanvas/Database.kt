package com.taskcanvas

import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

object Database {
    private val properties = Properties()

    init {
        val propertiesFile = "src/test/resources/gauge.properties"
        FileInputStream(propertiesFile).use { properties.load(it) }

        checkNotNull(properties.getProperty("db.jdbcUrl")) { "db.jdbcUrl is not set" }
        checkNotNull(properties.getProperty("db.username")) { "db.username is not set" }
        checkNotNull(properties.getProperty("db.password")) { "db.password is not set" }
    }

    private val jdbcUrl: String = properties.getProperty("db.jdbcUrl")
    private val username: String = properties.getProperty("db.username")
    private val password: String = properties.getProperty("db.password")

    fun connection(): Connection = DriverManager.getConnection(jdbcUrl, username, password)

    fun truncateAll() {
        connection().use { conn ->
            conn.createStatement().use { stmt ->
                val tables = listOf(
                    "task_canvas.user",
                    "task_canvas.todo",
                    "task_canvas.user_todo"
                )

                tables.joinToString(",").let { table ->
                    stmt.execute("TRUNCATE $table RESTART IDENTITY CASCADE;")
                }
            }
        }
    }
}