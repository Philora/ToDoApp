package com.todo.app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {ToDo.class}, version = 1, exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase{

    public abstract ToDoDao toDoDao();


    private static ToDoDatabase INSTANCE;

    public static ToDoDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (ToDoDatabase.class){
                INSTANCE = Room.databaseBuilder(context,ToDoDatabase.class,"todo_database").build();
            }
        }
        return INSTANCE;
    }
}
