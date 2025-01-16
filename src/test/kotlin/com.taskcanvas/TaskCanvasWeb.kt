package com.taskcanvas

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.open
import com.thoughtworks.gauge.Step

class TaskCanvasWeb {
    @Step("サインイン画面にアクセスする")
    fun サインイン画面にアクセスする() {
        open("http://localhost:3000/signin")
    }

    @Step("トップ画面にアクセスする")
    fun トップ画面にアクセスする() {
        open("http://localhost:3000/")
    }

    @Step("画面<url>にアクセスする")
    fun 画面にアクセスする(url: String) {
        open(url)
    }

    @Step("サインイン画面のタイトルにTask Canvasが表示されている")
    fun タイトルにTaskCanvasが表示されている() {
        Locator.getByRole(Role.Heading, "Task Canvas").first().shouldBe(visible)
    }

    @Step("見出し<text>が表示されている")
    fun 見出しが表示されている(text: String) {
        Locator.getByRole(Role.Heading, text).first().shouldBe(visible)
    }

    @Step("サインイン画面の説明文が表示されている")
    fun サインイン画面の説明文が表示されている() {
        Locator.getByRole(Role.Paragraph, "ユーザー情報を入力してください。").first().shouldBe(visible)
    }

    @Step("文章<text>が表示されている")
    fun 文章が表示されている(text: String) {
        Locator.getByRole(Role.Paragraph, text).first().shouldBe(visible)
    }

    @Step("リンク<text>が表示されている")
    fun リンクが表示されている(text: String) {
        Locator.getByRole(Role.Link, text).first().shouldBe(visible)
    }

    @Step("ボタン<text>が表示されている")
    fun ボタンが表示されている(text: String) {
        Locator.getByRole(Role.Button, text).first().shouldBe(visible)
    }

    @Step("メールアドレスの入力フォームが表示されている")
    fun メールアドレスの入力フォームが表示されている() {
        Locator.getByRoleAll(Role.Textbox)
            .filter(attribute("type", "email"))
            .find(attribute("placeholder", "メールを入力してください"))
            .shouldBe(visible)
    }

    @Step("パスワードの入力フォームが表示されている")
    fun パスワードの入力フォームが表示されている() {
        Locator.getByRoleAll(Role.Textbox)
            .filter(attribute("type", "password"))
            .find(attribute("placeholder", "パスワードを入力してください"))
            .shouldBe(visible)
    }

    @Step("サインインボタンが表示されている")
    fun サインボタンが表示されている() {
        Locator.getByRole(Role.Button, "サインイン").first().shouldBe(visible)
    }

    @Step("サインアップへの遷移リンクが表示されている")
    fun サインアップへの遷移リンクが表示されている() {
        Locator.getByRole(Role.Button, "サインアップ").first().shouldBe(visible)
    }

    @Step("メールの入力欄に<email>を入力する")
    fun メールアドレスに値を入力する(email: String) {
        Locator.getByRoleAll(Role.Textbox)
            .filter(attribute("type", "email"))
            .filter(attribute("placeholder", "メールを入力してください"))
            .first()
            .sendKeys(email)
    }

    @Step("パスワードの入力欄に<password>を入力する")
    fun パスワードに値を入力する(password: String) {
        Locator.getByRoleAll(Role.Textbox)
            .filter(attribute("type", "password"))
            .filter(attribute("placeholder", "パスワードを入力してください"))
            .first()
            .sendKeys(password)
    }

    @Step("サインインボタンをクリックする")
    fun サインインボタンをクリックする() {
        Locator.getByRole(Role.Button, "サインイン").first().click()
    }

    @Step("ローディングが表示される")
    fun ローディングが表示される() {
        Locator.getByRoleAll(Role.Div).filter(attribute("aria-busy",  "true")).first().shouldBe(visible)
    }

    @Step("ローディングが表示されていない")
    fun ローディングが表示されていない() {
        Locator.getByRoleAll(Role.Alert).filter(attribute("aria-busy",  "true")).first().shouldNotBe(visible)
    }

    @Step("サインインの成功通知が表示される")
    fun サインインの成功通知が表示される() {
        Locator.getByRoleAll(Role.Alert).filter(attribute("aria-label",  "ログインに成功しました")).first().shouldBe(visible)
    }

    @Step("TODO一覧ページが表示されている")
    fun Todo一覧ページが表示されている() {
        Locator.getByRole(Role.Paragraph, "My Todo-s").first().shouldBe(visible)
    }
}

