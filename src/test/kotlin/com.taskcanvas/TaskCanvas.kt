package com.taskcanvas

import com.sun.net.httpserver.Headers
import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.datastore.DataStore
import oracle.ucp.proxy.annotation.Post
import org.assertj.core.api.Assertions.assertThat
import java.io.FileInputStream
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.URI
import java.util.Properties

class TaskCanvas {
    private val baseUrl = readBaseUrl()
    private val client = HttpClient.newHttpClient()
    private lateinit var response: HttpResponse<String>
    private lateinit var authorizationToken: String

    @Step("v1/systems/pingにリクエストを送るとpongが返ってくる")
    fun pingPong() {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint("/v1/systems/ping"))
            .GET()
            .build()

        response = client.send(request, HttpResponse.BodyHandlers.ofString())

        assertThat( response.body() ).isEqualTo("pong")
    }

    @Step("ステータスコードが<status>である")
    fun statusCode(status: Int) {
        assertThat(response.statusCode()).isEqualTo(status)
    }

    @Step("レスポンスヘッダのAuthorizationにトークンが含まれている")
    fun responseAuthorization() {
        assertThat(response.headers().firstValue("Authorization").get().isNotEmpty())
    }

    @Step("URL<url>にボディ<requestBody>で、POSTリクエストを送る")
    fun sendRequest(url: String, requestBody: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))

        response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        authorizationToken = response.headers().firstValue("Authorization").get()
    }

    @Step("URL<url>にAuthorizationTokenを含めてGETリクエストを送る")
    fun sendGetRequest(url: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint(url))
            .header("Authorization", authorizationToken)
            .GET()

        response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        authorizationToken = response.headers().firstValue("Authorization").get()
    }


    private fun readBaseUrl(): String {
       val  properties = Properties()
       val propertiesFile = "src/test/resources/gauge.properties"
        FileInputStream(propertiesFile).use { properties.load(it) }
        return properties.getProperty("rest.baseUrl")
    }

    private fun generateEndpoint(url: String): URI {
        return URI.create("$baseUrl$url")
    }
}