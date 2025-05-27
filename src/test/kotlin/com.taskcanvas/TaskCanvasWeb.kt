package com.taskcanvas

import com.codeborne.selenide.Condition.*
import com.codeborne.selenide.Selenide.open
import com.thoughtworks.gauge.Step

class TaskCanvasWeb {
    @Step("画面<url>にアクセスする")
    fun 画面にアクセスする(url: String) {
        open(url)
    }

    @Step("見出し<text>が表示されている")
    fun 見出しが表示されている(text: String) {
        Locator.getByRole(Role.Heading, text).first().shouldBe(visible)
    }

    @Step("文章<text>が表示されている")
    fun 文章が表示されている(text: String) {
        Locator.getByRole(Role.Paragraph, text).first().shouldBe(visible)
    }

    @Step("文章<text>が表示されていない")
    fun 文章が表示されていない(text: String) {
        Locator.getByRole(Role.Paragraph, text).first().shouldNotBe(visible)
    }

    @Step("リンク<text>が表示されている")
    fun リンクが表示されている(text: String) {
        Locator.getByRole(Role.Link, text).first().shouldBe(visible)
    }

    @Step("リンク<text>をクリックする")
    fun リンクをクリックする(text: String) {
        Locator.getByRole(Role.Link, text).first().click()
    }

    @Step("ボタン<text>が表示されている")
    fun ボタンが表示されている(text: String) {
        Locator.getByRole(Role.Button, text).first().shouldBe(visible)
    }

    @Step("アイコン<name>が表示されている")
    fun アイコンが表示されている(name: String) {
        Locator.getByRole(Role.Svg, name).first().shouldBe(visible)
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

    @Step("ボタン<text>をクリックする")
    fun ボタンをクリックする(text: String) {
        Locator.getByRole(Role.Button, text).first().click()
    }

    @Step("ボタン<text>が活性である")
    fun ボタンが活性である(text: String) {
        Locator.getByRole(Role.Button, text).first().shouldNotHave(attribute("disabled"))
    }

    @Step("ボタン<text>が非活性である")
    fun ボタンが非活性である(text: String) {
        Locator.getByRole(Role.Button, text).first().shouldHave(attribute("disabled"))
    }

    @Step("フォーム<formLabel>のボタン<buttonText>をクリックする")
    fun フォームのボタンをクリックする(formLabel: String, buttonText: String) {
        val form = Locator.getByRole(Role.Form, formLabel).first()
        form.`$$`("button")
            .filter(exactText(buttonText))
            .first()
            .click()
    }

    @Step("フォーム<formLabel>のInput<name>にテキスト<text>を入力する")
    fun フォームのInputにテキストを入力する(formLabel: String, name: String, text: String) {
        val form = Locator.getByRole(Role.Form, formLabel).first()
        form.`$$`("input")
            .filter(attribute("name", name))
            .first()
            .sendKeys(text)
    }

    @Step("フォーム<formLabel>のボタン<buttonText>の属性<attribute>が<value>である")
    fun フォームのボタンの属性がである(formLabel: String, buttonText: String, attribute: String, value: String) {
        val form = Locator.getByRole(Role.Form, formLabel).first()
        form.`$$`("button")
            .filter(exactText(buttonText))
            .first()
            .shouldHave(attribute(attribute, value))
    }

    @Step("フォーム<formLabel>のボタン<buttonText>の属性<attribute>が存在しない")
    fun フォームのボタンの属性が存在しない(formLabel: String, buttonText: String, attribute: String) {
        val form = Locator.getByRole(Role.Form, formLabel).first()
        form.`$$`("button")
            .filter(exactText(buttonText))
            .first()
            .shouldNotHave(attribute(attribute))
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に要素<element>が表示されている")
    fun リストのリストアイテムに要素が表示されている(listRoleLabel: String, text: String, listItemRoleLabel: String, element: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .`$`(element)
            .shouldBe(visible)
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に要素<element>が存在している")
    fun リストのリストアイテムに要素が存在している(listRoleLabel: String, text: String, listItemRoleLabel: String, element: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .`$`(element)
            .should(exist)
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に要素<element>が表示されていない")
    fun リストのリストアイテムに要素が表示されていない(listRoleLabel: String, text: String, listItemRoleLabel: String, element: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .`$`(element)
            .shouldNotBe(visible)
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に要素<element>の属性<attribute>が<value>である")
    fun リストにリストアイテムの要素の属性がである(
        listRoleLabel: String, text: String, listItemRoleLabel: String, element: String, attribute: String, value: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        val targetElement = list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .`$`(element)

        if (attribute == "checked") {
            if (value == "true") {
                targetElement.shouldBe(checked)
            } else {
                targetElement.shouldNotBe(checked)
            }
        } else {
            targetElement.shouldHave(attribute(attribute, value))
        }
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に要素<element>の属性<attribute>が存在する")
    fun リストにリストアイテムの要素の属性が存在する(listRoleLabel: String, listItemRoleLabel: String, text: String, element: String, attribute: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(attribute("aria-label", listItemRoleLabel))
            .filter(exactText(text))
            .first()
            .`$`(element)
            .shouldHave(attribute(attribute))
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に要素<element>の属性<attribute>が存在しない")
    fun リストにリストアイテムの要素の属性が存在しない(listRoleLabel: String, listItemRoleLabel: String, text: String, element: String, attribute: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(attribute("aria-label", listItemRoleLabel))
            .filter(exactText(text))
            .first()
            .`$`(element)
            .shouldNotHave(attribute(attribute))
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に要素<element>をクリックする")
    fun リストのリストアイテムに要素をクリックする(listRoleLabel: String, text: String, listItemRoleLabel: String, element: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .`$`(element)
            .click()
    }

    @Step("リスト<listRoleLabel>にテキスト<text>のリストアイテム<listItemRoleLabel>が表示されている")
    fun リストの要素が表示されている(listRoleLabel: String, text: String, listItemRoleLabel: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .shouldBe(visible)
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>の要素<element>にカーソルを合わせる")
    fun リストの要素にカーソルを合わせる(listRoleLabel: String, text: String, listItemRoleLabel: String, element: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .`$`(element)
            .hover()
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に属性<attribute>が<value>の要素が表示されている")
    fun リストの要素が表示されている(listRoleLabel: String, text: String, listItemRoleLabel: String, attribute: String, value: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .shouldHave(attribute(attribute, value))
    }

    @Step("リスト<listRoleLabel>のテキスト<text>のリストアイテム<listItemRoleLabel>に属性<attribute>が<value>の要素が表示されていない")
    fun リストの要素が表示されていない(listRoleLabel: String, text: String, listItemRoleLabel: String, attribute: String, value: String) {
        val list = Locator.getByRole(Role.List, listRoleLabel).first()
        list.`$$`("li")
            .filter(exactText(text))
            .filter(attribute("aria-label", listItemRoleLabel))
            .first()
            .shouldNotHave(attribute(attribute, value))
    }

    @Step("ローディングが表示される")
    fun ローディングが表示される() {
        Locator.getByRoleAll(Role.Div).filter(attribute("aria-busy", "true")).first().shouldBe(visible)
    }

    @Step("ローディングが表示されていない")
    fun ローディングが表示されていない() {
        Locator.getByRoleAll(Role.Alert).filter(attribute("aria-busy", "true")).first().shouldNotBe(visible)
    }

    @Step("アラート<text>が表示されている")
    fun アラートが表示されている(text: String) {
        Locator.getByRole(Role.Alert, text).first().shouldBe(visible)
    }
}

