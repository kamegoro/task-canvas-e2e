package com.taskcanvas

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addEnvironmentSource
import com.sksamuel.hoplite.addResourceSource
import com.sksamuel.hoplite.yaml.YamlParser

private const val RESOURCE = "/env.yaml"

val config = ConfigLoaderBuilder.default()
    .addEnvironmentSource()
    .addResourceSource(RESOURCE)
    .addDefaultParsers()
    .build()
    .loadConfigOrThrow<Config>()

data class Config(
    val taskCanvas: TaskCanvasConfig,
    val taskCanvasTagManager: TaskCanvasTagManagerConfig
)

data class TaskCanvasConfig(
    val rest: TaskCanvasApiConfig,
    val db: TaskCanvasDbConfig
)

data class TaskCanvasApiConfig(val baseUrl: String)
data class TaskCanvasDbConfig(
    val jdbcUrl: String,
    val username: String,
    val password: String,
    val schema: String
)

data class TaskCanvasTagManagerConfig(
    val rest: TaskCanvasTagManagerApiConfig
)

data class TaskCanvasTagManagerApiConfig(val baseUrl: String)