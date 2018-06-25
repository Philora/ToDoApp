package com.todo.app;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "todo_table")
public class ToDo{

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "todo_name")
    private String todoName;

    @ColumnInfo(name = "todo_date")
    private String todoDate;


    public ToDo(String todoName, String todoDate) {
        this.todoName = todoName;
        this.todoDate = todoDate;
    }

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

}

