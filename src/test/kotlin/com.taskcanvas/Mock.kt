package com.taskcanvas

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import java.io.File

object TaskCanvasApiForWeb {
    private val mockInstance: WireMock = WireMock(config.taskCanvasWeb.backendHost, config.taskCanvasWeb.backendPort)

    fun resetToDefaultMappings() {
        mockInstance.resetToDefaultMappings()
    }

    fun loadMapping(file: File) {
        mockInstance.loadMappingsFrom(file)
    }
}