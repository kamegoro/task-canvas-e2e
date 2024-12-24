package com.taskcanvas

import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

object Database {
    private val jdbcUrl: String = config.taskCanvas.db.jdbcUrl
    private val username: String = config.taskCanvas.db.username
    private val password: String = config.taskCanvas.db.password

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