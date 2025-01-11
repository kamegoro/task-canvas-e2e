package com.taskcanvas

import com.codeborne.selenide.Condition.attribute
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.open
import com.thoughtworks.gauge.Step

class TaskCanvasWeb {
    @Step("サインインページにアクセスする")
    fun サインイン画面にアクセスする() {
        open("http://localhost:3000/signin")
    }

    @Step("サインイン画面のタイトルにTask Canvasが表示されている")
    fun タイトルにTaskCanvasが表示されている() {
        Locator.getByRole(Role.Heading, "Task Canvas").first().shouldBe(visible)
    }

    @Step("サインイン画面の説明文が表示されている")
    fun サインイン画面の説明文が表示されている() {
        Locator.getByRole(Role.Paragraph, "ユーザー情報を入力してください。").first().shouldBe(visible)
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
}
