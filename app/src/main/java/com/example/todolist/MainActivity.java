package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> taskArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskArray.add(new Task("task 1", new Date(), false));
        taskArray.add(new Task("task 2", new Date(), true));
        taskArray.add(new Task("task 1", new Date(), false));
        taskArray.add(new Task("task 2", new Date(), true));
        taskArray.add(new Task("task 1", new Date(), false));
        taskArray.add(new Task("task 2", new Date(), true));
        taskArray.add(new Task("task 1", new Date(), false));
        taskArray.add(new Task("task 2", new Date(), true));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        TasksListAdapter adapter = new TasksListAdapter(this, taskArray);
        NoScrollListView itemsListView  = findViewById(R.id.task_list_view);
        itemsListView.setAdapter(adapter);

    }
}
