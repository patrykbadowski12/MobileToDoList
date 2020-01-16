package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> taskArray = new ArrayList<>();
    TextView addTask;
    DatePicker datePicker;
    TimePicker timePicker;
    TasksListAdapter adapter;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (pref != null) {
            Gson gson = new Gson();
            Task[] tasks = gson.fromJson(pref.getString("tasks", null), Task[].class);
            taskArray = new ArrayList<>(Arrays.asList(tasks));
        }
        if (taskArray == null) {
            taskArray = new ArrayList<>();
            System.out.println(pref.getString("tasks",null));
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        adapter = new TasksListAdapter(this, taskArray);
        NoScrollListView itemsListView  = findViewById(R.id.task_list_view);
        itemsListView.setAdapter(adapter);

        NoScrollListView list = findViewById(R.id.task_list_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
            }
        });
    }

    public void addTask(View view) {
        addTask = findViewById(R.id.editText2);
        datePicker = findViewById(R.id.simpleDatePicker);
        int dayOfMonth = datePicker.getDayOfMonth() + 1;
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        timePicker = findViewById(R.id.timePicker);
        int minute = timePicker.getCurrentMinute();
        Integer currentHour = timePicker.getCurrentHour();
        Date date = new Date(year,month,dayOfMonth,currentHour,minute);

        Task newTask = new Task(addTask.getText().toString(), date, false);
        taskArray.add(newTask);
        saveTasks();
        adapter.notifyDataSetChanged();
    }

    public void saveTasks() {
        SharedPreferences.Editor edit = pref.edit();
        Gson gson = new Gson();
        String s = gson.toJson(taskArray);
        edit.putString("tasks",s);
        edit.apply();
    }

}
