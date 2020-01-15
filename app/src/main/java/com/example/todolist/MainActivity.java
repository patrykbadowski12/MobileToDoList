package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> taskArray = new ArrayList<>();
    TextView addTask;
    DatePicker datePicker;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskArray.add(new Task("task 1", new Date(), false));
        taskArray.add(new Task("task 2", new Date(), true));
        taskArray.add(new Task("task 1", new Date(), false));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        TasksListAdapter adapter = new TasksListAdapter(this, taskArray);
        NoScrollListView itemsListView  = findViewById(R.id.task_list_view);
        itemsListView.setAdapter(adapter);
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
        System.out.println(newTask);
        System.out.println(taskArray);
    }
}
