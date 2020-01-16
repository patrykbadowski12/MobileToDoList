package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TasksListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Task> tasksList;
    private CheckBox checkBox;

    public TasksListAdapter(Context context, ArrayList<Task> tasksList) {
        this.context = context;
        this.tasksList = tasksList;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int position) {
        return tasksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_list_view_row_items, parent, false);
        }

        Task currentTask = (Task) getItem(position);

        TextView textViewTaskContent = convertView.findViewById(R.id.text_view_item_content);
        TextView textViewTaskDate = convertView.findViewById(R.id.text_view_item_date);
        checkBox = convertView.findViewById(R.id.text_view_item_done);

        textViewTaskContent.setText(currentTask.getContent());
        textViewTaskDate.setText(currentTask.getDate().toString().substring(0,16));
        checkBox.setChecked(currentTask.isDone());

        return convertView;
    }
}
