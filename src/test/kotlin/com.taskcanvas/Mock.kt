package com.taskcanvas

import com.github.tomakehurst.wiremock.client.WireMock

object TaskCanvasApiForWeb : WireMock(config.taskCanvasWeb.backendHost, config.taskCanvasWeb.backendPort)