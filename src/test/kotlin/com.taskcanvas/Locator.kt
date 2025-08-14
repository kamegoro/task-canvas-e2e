package com.taskcanvas

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.Selenide.`$$x`

object Locator {
    fun getByRole(role: Role, accessibleName: String): ElementsCollection {
        return `$$x`(roleToXpath(role, accessibleName))
    }

    fun getByRoleAll(role: Role): ElementsCollection {
        return `$$x`(roleToXpathAll(role))
    }

    private fun roleToXpath(role: Role, accessibleName: String): String {
        val xPaths = when (role) {
            Role.Alert -> role.possibleElements().map {
                "//$it[@aria-label='$accessibleName' and @role='alert']"
            }

            Role.Form -> role.possibleElements().map {
                "//$it[@aria-label='$accessibleName']"
            }

            Role.List -> role.possibleElements().map {
                "//$it[@aria-label='$accessibleName']"
            }

            Role.Svg -> role.possibleElements().map {
                "//$it[@aria-label='$accessibleName']"
            }

            else -> role.possibleElements().map {
                "//$it[text()='${accessibleName}']"
            }
        }

        return xPaths.joinToString(" | ")
    }

    private fun roleToXpathAll(role: Role): String {
        val xPaths = when (role) {
            Role.Alert -> role.possibleElements().map {
                "//$it[@role='alert']"
            }

            Role.Menu -> role.possibleElements().map {
                "//$it[@role='menu']"
            }

            else -> role.possibleElements().map {
                "//$it"
            }
        }

        return xPaths.joinToString(" | ")
    }
}

enum class Role {
    Header,
    List,
    Form,
    Button,
    Heading,
    Paragraph,
    Link,
    Alert,
    Div,
    Svg,
    Menu,
    Textbox;

    fun possibleElements() = when (this) {
        Header -> listOf("header")
        List -> listOf("ul")
        Form -> listOf("form")
        Button -> listOf("button")
        Heading -> listOf("h1", "h2", "h3", "h4", "h5", "h6")
        Paragraph -> listOf("p")
        Link -> listOf("a")
        Alert -> listOf("div")
        Div -> listOf("div")
        Svg -> listOf("svg")
        Menu -> listOf("menu", "ul")
        Textbox -> listOf("input")
    }
}
