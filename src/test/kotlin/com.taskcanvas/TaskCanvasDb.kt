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
    fun checkRecordNotExist(tableName: String, columnName: String, value: String) {
        Database.connection().createStatement().use { stmt ->
            val sql = "SELECT * FROM task_canvas.$tableName"
            val results = stmt.executeQuery(sql)

            if (!results.next()) {
                throw AssertionError("Record not found")
            }

            val actual = results.getString(columnName)
            assertThat(actual).isNotEqualTo(value)
        }
    }
}
