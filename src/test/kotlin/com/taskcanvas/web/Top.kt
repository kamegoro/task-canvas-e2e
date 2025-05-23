package com.taskcanvas.web

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.Selenide.`$$`
import com.thoughtworks.gauge.Step

class Top {
    @Step("TODOの入力フォームが表示されている")
    fun todoの入力フォームが表示されている() {
        `$$`("form").findBy(attribute("aria-label", "todo-form")).shouldBe(visible)
    }

    @Step("TODOの入力フォームのプレースホルダーに<text>と表示されている")
    fun todoの入力フォームのプレースホルダーにが表示されている(text: String) {
        `$$`("form").findBy(attribute("aria-label", "todo-form"))
            .`$`("input")
            .shouldHave(attribute("placeholder", text))
    }

    @Step("TODOの送信ボタンが表示されている")
    fun todoの送信ボタンが表示されている() {
        `$$`("form").findBy(attribute("aria-label", "todo-form"))
            .`$`("button")
            .shouldHave(exactText("Add"))
            .shouldBe(visible)
    }

    @Step("TODOの送信ボタンが非活性である")
    fun todoの送信ボタンが非活性である() {
        `$$`("form").findBy(attribute("aria-label", "todo-form"))
            .`$`("button")
            .shouldHave(exactText("Add"))
            .shouldHave(disabled)
    }

    @Step("TODOの入力フォームにカレンダーアイコンが表示されている")
    fun todoの入力フォームにカレンダーアイコンが表示されている() {
        `$$`("form").findBy(attribute("aria-label", "todo-form"))
            .`$`("svg")
            .shouldHave(attribute("name", "calendar"))
            .shouldBe(visible)
    }

    @Step("TODOの進捗率が表示されている")
    fun todoの進捗率が表示されている() {
        `$`("task-progress").shouldBe(visible)
    }

    @Step("Input<name>にテキスト<value>を入力する")
    fun inputにテキストを入力する(name: String, value: String) {
        `$$`("input").findBy(attribute("name", name))
            .sendKeys(value)
    }
}