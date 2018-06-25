package com.todo.app;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    EditText et_header;
    DateFormat dateFormat;
    Button btnAddNew;
    private ToDoViewModel toDoViewModel;
    private ToDoAdapter toDoAdapter;
    private RecyclerView recyclerView;
    ToDoDatabase toDoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);
        toDoAdapter = new ToDoAdapter( new ArrayList<ToDo>(),this , toDoViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnAddNew = findViewById(R.id.btn_addNew);
        recyclerView.setAdapter(toDoAdapter);

        toDoViewModel.getToDo().observe(MainActivity.this, new Observer<List<ToDo>>() {

            @Override
            public void onChanged(@Nullable List<ToDo> toDos) {
                toDoAdapter.setTodoList(toDos);
            }
        });
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNew(v);
            }
        });
    }

    public void addNew(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Welcome TODO App");
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_popup, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        et_header = dialogView.findViewById(R.id.edtHeader);
        dateFormat = new SimpleDateFormat("dd-MMM-yyyy, HH:mm");
        final String header = et_header.getText().toString();
        final String date = dateFormat.format(Calendar.getInstance().getTime());

        Log.d(TAG, "EditText Value" + et_header);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                if (toDoViewModel != null && !TextUtils.isEmpty(et_header.getText())) {
                    toDoViewModel.addTodo(new ToDo(et_header.getText().toString(), date));

                } else {
                    Toast.makeText(getApplicationContext(), "Header cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
    }
}