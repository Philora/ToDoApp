package com.todo.app;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

public class ToDoViewModel extends AndroidViewModel {

    private final LiveData<List<ToDo>> listLiveData;

    private ToDoDatabase toDoDatabase;

    public ToDoViewModel(Application application) {
        super(application);

        toDoDatabase = ToDoDatabase.getDatabase(this.getApplication());

        listLiveData = toDoDatabase.toDoDao().getTodoList();
    }

    public void addTodo(ToDo toDo) {
        new addTodoAsyncTask(toDoDatabase).execute(toDo);
    }

    private class addTodoAsyncTask extends AsyncTask<ToDo, Void, Void> {

        ToDoDatabase doDatabase;
        public addTodoAsyncTask(ToDoDatabase toDoDatabase) {
            doDatabase = toDoDatabase;
        }
        @Override
        protected Void doInBackground(ToDo... toDos) {
            toDoDatabase.toDoDao().addnewTodo(toDos[0]);
            return null;
        }
    }


    public LiveData<List<ToDo>> getToDo() {
        return listLiveData;
    }

    public void deleteItem(ToDo toDoDel) {
        new deleteAsyncTask().execute(toDoDel);
    }

    private class deleteAsyncTask extends AsyncTask<ToDo, Void, Void> {

        @Override
        protected Void doInBackground(final ToDo... params) {
            toDoDatabase.toDoDao().deleteToDo(params[0]);
            return null;
        }

    }
}