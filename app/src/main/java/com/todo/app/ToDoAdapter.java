package com.todo.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    private static final String TAG = "ToDoAdapter";
    Context context;
    private ToDoViewModel toDoGetViewModel;
    private List<ToDo> toDoList;

    public ToDoAdapter(ArrayList<ToDo> toDos, Context context, ToDoViewModel todoGetMv) {
        this.toDoList = toDos;
        this.context = context;
        this.toDoGetViewModel = todoGetMv;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ToDo todoPos = toDoList.get(position);
        holder.header.setText(todoPos.getTodoName());
        holder.timestamp.setText(todoPos.getTodoDate());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDo deleteTodoItem = toDoList.get(holder.getAdapterPosition());
                if (toDoGetViewModel != null) {
                    toDoGetViewModel.deleteItem(deleteTodoItem);
                    Toast.makeText(context, "Deleting the Header: " + todoPos.getTodoName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public void setTodoList(List<ToDo> todoList) {
        this.toDoList = todoList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView header, timestamp;
        ImageView imgDelete;


        public MyViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.txtheader);
            timestamp = (TextView) view.findViewById(R.id.txttimestamp);
            imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
        }
    }

}