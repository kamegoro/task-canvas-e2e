package com.taskcanvas

import org.dbunit.database.DatabaseConfig
import org.dbunit.database.DatabaseConnection
import org.dbunit.database.IDatabaseConnection
import java.sql.DriverManager
import java.util.Properties

object TaskCanvasDb {
    private val properties = Properties()

    init {
        val propertiesStream = this::class.java.classLoader.getResourceAsStream("gauge.properties")
        propertiesStream.let {
            properties.load(it)
        }
    }

    private val jdbcUrl: String = properties.getProperty("jdbcUrl")
    private val username: String = properties.getProperty("username")
    private val password: String = properties.getProperty("password")

    private val connection: IDatabaseConnection = DatabaseConnection(
        DriverManager.getConnection(jdbcUrl, username, password)
    ).apply {
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true)
    }
}