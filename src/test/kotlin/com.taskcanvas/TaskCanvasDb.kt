package com.taskcanvas

import com.thoughtworks.gauge.Step
import org.assertj.core.api.Assertions.assertThat

class TaskCanvasDb {
    @Step("TaskCanvasDBのテーブル<tableName>のカラム<columnName>に<value>のレコードが存在する")
    fun checkRecord(tableName: String, columnName: String, value: String) {
       Database.connection().createStatement().use { stmt ->
           val sql = "SELECT * FROM task_canvas.$tableName WHERE $columnName = '$value'"
           val result = stmt.executeQuery(sql)

           if (!result.next()) {
               throw AssertionError("Record not found")
           }

           val actual = result.getString(columnName)
           assertThat(actual).isEqualTo(value)
       }
    }

    @Step("TaskCanvasDBのテーブル<tableName>のカラム<columnName>に<value>のレコードが存在しない")
    fun emptyRecord(tableName: String, columnName: String, value: String) {
        Database.connection().createStatement().use { stmt ->
            val sql = "SELECT * FROM task_canvas.$tableName WHERE $columnName = '$value'"
            val result = stmt.executeQuery(sql)

            assertThat(result.next()).isFalse()
        }
    }
}
