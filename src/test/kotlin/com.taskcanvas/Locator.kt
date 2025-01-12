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
            else -> role.possibleElements().map {
                "//$it[text()='${accessibleName}']"
            }
        }

        return xPaths.joinToString(" | ")
    }

    private fun roleToXpathAll(role: Role): String {
        val xPaths =
            role.possibleElements().map {
                "//$it"
            }


        return xPaths.joinToString(" | ")
    }
}

enum class Role {
    Button,
    Heading,
    Paragraph,
    Link,
    Alert,
    Textbox;

    fun possibleElements() = when (this) {
        Button -> listOf("button")
        Heading -> listOf("h1", "h2", "h3", "h4", "h5", "h6")
        Paragraph -> listOf("p")
        Link -> listOf("a")
        Alert -> listOf("section")
        Textbox -> listOf("input")
    }
}
