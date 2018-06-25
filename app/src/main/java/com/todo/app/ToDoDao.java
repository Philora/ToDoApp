package com.todo.app;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public  interface ToDoDao {

    @Insert
    public void addnewTodo(ToDo toDo);

    @Query("SELECT * from todo_table")
    LiveData<List<ToDo>> getTodoList();

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Delete
    void deleteToDo(ToDo... toDo);

}
