package com.taskcanvas

import com.jayway.jsonpath.JsonPath
import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.datastore.ScenarioDataStore
import org.assertj.core.api.Assertions.assertThat
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.URI

class TaskCanvas {
    private val baseUrl = readBaseUrl()
    private val client = HttpClient.newHttpClient()
    private lateinit var response: HttpResponse<String>
    private lateinit var authorizationToken: String

    @Step("レスポンスボディにテキスト<text>が含まれている")
    fun responseBodyContainsText(text: String) {
        assertThat(response.body()).contains(text)
    }

    @Step("ステータスコードが<status>である")
    fun statusCode(status: Int) {
        assertThat(response.statusCode()).isEqualTo(status)
    }

    @Step("レスポンスヘッダのAuthorizationにトークンが含まれている")
    fun responseAuthorization() {
        assertThat(response.headers().firstValue("Authorization").get().isNotEmpty())
    }

    @Step("API<apiName>のURL<url>にGETリクエストを送る")
    fun setGetRequestToTargetApi(apiName: String, url: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateApiEndpoint(apiName, url))
            .GET()

        println("URL: ${generateApiEndpoint(apiName, url)}")

        response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        println("Response: ${response.body()}")
    }

    @Step("URL<url>にボディ<requestBody>で、POSTリクエストを送る")
    fun sendRequest(url: String, requestBody: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint(url))
            .headers("Content-Type", "application/json", "Authorization", authorizationToken)
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))

        response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        authorizationToken = response.headers().firstValue("Authorization").get()
    }

    @Step("URL<url>にボディ<requestBody>で、PUTリクエストを送る")
    fun sendPutRequest(url: String, requestBody: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint(url))
            .headers("Content-Type", "application/json", "Authorization", authorizationToken)
            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))

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

    @Step("URL<url>にAuthorizationTokenを含めてPOSTリクエストを送る")
    fun sendPostRequestOnlyAuthorization(url: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint(url))
            .header("Authorization", authorizationToken)
            .POST(HttpRequest.BodyPublishers.noBody())

        response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        ScenarioDataStore.put("returnedToken", authorizationToken)
    }

    @Step("URL<url>にボディ<body>で、AuthorizationTokenを含めずにPOSTリクエストを送る")
    fun sendPostRequestWithoutAuthorization(url: String, body: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))

        response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        authorizationToken = response.headers().firstValue("Authorization").get()
    }

    @Step("URL<url>にDELETEリクエストを送る")
    fun sendDeleteRequest(url: String) {
        val request = HttpRequest.newBuilder()
            .uri(generateEndpoint(url))
            .header("Authorization", authorizationToken)
            .DELETE()

        response = client.send(request.build(), HttpResponse.BodyHandlers.ofString())
        authorizationToken = response.headers().firstValue("Authorization").get()
    }

    @Step("レスポンスボディにJSONでキー<key>で値<value>が含まれている")
    fun responseBodyContains(key: String, value: String) {
        assertThat(response.body()).contains("\"$key\":\"$value\"")
    }

    @Step("レスポンスボディにJSONでキー<arrayKey>の配列の中にキー<key>で値<value>が含まれていない")
    fun responseBodyNotContains(arrayKey: String, key: String, value: String) {
        val document = JsonPath.parse(response.body())
        val array = document.read<List<Any>>(arrayKey)
        assertThat(array).noneMatch { JsonPath.read<Any>(it, key) == value }
    }

    @Step("レスポンスボディにJSONでキー<key>が含まれている")
    fun responseBodyContainsKey(key: String) {
        val document = JsonPath.parse(response.body())
        val value = document.read<Any>(key)
        assertThat(value).isNotNull
    }

    @Step("返却されたトークンがリクエストに使ったトークンと異なる")
    fun tokenIsDifferent() {
        assertThat(ScenarioDataStore.get("returnedToken")).isNotEqualTo(authorizationToken)
    }

    @Step("pass")
    fun pass() {
        return
    }

    private fun readBaseUrl(): String {
        return config.taskCanvas.rest.baseUrl
    }

    private fun generateEndpoint(url: String): URI {
        return URI.create("$baseUrl$url")
    }

    private fun generateApiEndpoint(apiName: String, url: String): URI {
        if (apiName == "taskCanvas") {
            return URI.create("${config.taskCanvas.rest.baseUrl}$url")
        }

        if (apiName == "taskCanvasTagManager") {
            return URI.create("${config.taskCanvasTagManager.rest.baseUrl}$url")
        }

        throw IllegalArgumentException("Invalid API name")
    }
}