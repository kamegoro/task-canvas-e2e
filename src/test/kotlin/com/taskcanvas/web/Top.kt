package com.taskcanvas.web

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.*
import com.taskcanvas.Locator
import com.taskcanvas.Role
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

    @Step("Input<name>の入力値が<value>である")
    fun inputの入力値がである(name: String, value: String) {
        `$$`("input").findBy(attribute("name", name))
            .shouldHave(value(value))
    }

    @Step("Input<name>の内容を<value>に変更する")
    fun inputの内容をに変更する(name: String, value: String) {
        // input.clear() sets the DOM value without going through a real keyboard
        // event, so a React-controlled input's onChange never fires and its state
        // stays at the old value. On the next render React resyncs the DOM back to
        // that stale value, so sendKeys() ends up appending to the old text instead
        // of replacing it. setValue() clears via selectAll+Delete key events instead,
        // which React does see.
        val input = `$$`("input").findBy(attribute("name", name))
        input.setValue(value)
    }

    @Step("メニューが表示されている")
    fun メニューが表示されている() {
        Locator.getByRoleAll(Role.Menu).first().shouldBe(visible)
    }

    @Step("メニューが表示されていない")
    fun メニューが表示されていない() {
        Locator.getByRoleAll(Role.Menu).first().shouldNotBe(visible)
    }

    @Step("ヘッダーの端をマウスでクリックする")
    fun ヘッダーの端をマウスでクリックする() {
        val header = Locator.getByRoleAll(Role.Header).first()

        actions()
            .moveToElement(header, 0, 0)
            .click()
            .perform()
    }

    @Step("メニューに<text>が表示されている")
    fun メニューにが表示されている(text: String) {
        Locator.getByRoleAll(Role.Menu).filter(exactText(text))
            .first()
            .shouldBe(visible)
    }

    @Step("メニューの<text>をクリックする")
    fun メニューのをクリックする(text: String) {
        Locator.getByRoleAll(Role.Menu).first()
            .`$$`("li")
            .filter(exactText(text))
            .first()
            .click()
    }

    @Step("ダークモード切り替えボタンが表示されている")
    fun ダークモード切り替えボタンが表示されている() {
        `$$`("button").findBy(attribute("aria-label", "ダークモードに切り替え")).shouldBe(visible)
    }

    @Step("ダークモード切り替えボタンをクリックする")
    fun ダークモード切り替えボタンをクリックする() {
        `$$`("button").findBy(attribute("aria-label", "ダークモードに切り替え")).click()
    }

    @Step("ライトモード切り替えボタンが表示されている")
    fun ライトモード切り替えボタンが表示されている() {
        `$$`("button").findBy(attribute("aria-label", "ライトモードに切り替え")).shouldBe(visible)
    }
}