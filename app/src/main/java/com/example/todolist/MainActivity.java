package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNEL";

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
            if (tasks != null) {
                taskArray = new ArrayList<>(Arrays.asList(tasks));
            }
        }
        if (taskArray == null) {
            taskArray = new ArrayList<>();
            System.out.println(pref.getString("tasks", null));
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        adapter = new TasksListAdapter(this, taskArray);
        NoScrollListView itemsListView = findViewById(R.id.task_list_view);
        itemsListView.setAdapter(adapter);
        final Timer timerObj = new Timer();
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
                Date date = new Date();
                Timestamp nowPlus2 = new Timestamp(date.getTime());
                Timestamp now = new Timestamp(date.getTime());
                nowPlus2.setHours(nowPlus2.getHours()+2);

                for (Task task : taskArray) {
                    Timestamp taskTime = new Timestamp(task.getDate().getTime());
                    taskTime.setYear(taskTime.getYear()-1900);
                    if (taskTime.getTime() < nowPlus2.getTime() && !(taskTime.getTime() < now.getTime()) && !task.isSentNotification() && !task.isDone()){
                        task.setSentNotification(true);
                        pushNotification(task);
                    }
                }
                saveTasks(taskArray, pref);
            }
        };
        timerObj.schedule(timerTaskObj, 0, 60000);
    }

    public void addTask(View view) {
        addTask = findViewById(R.id.editText2);
        datePicker = findViewById(R.id.simpleDatePicker);
        int dayOfMonth = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        timePicker = findViewById(R.id.timePicker);
        int minute = timePicker.getCurrentMinute();
        Integer currentHour = timePicker.getCurrentHour();
        Date date = new Date(year, month, dayOfMonth, currentHour, minute);
        Task newTask = new Task(addTask.getText().toString(), date, false, false);
        taskArray.add(newTask);
        Toast.makeText(getApplicationContext(),"Task added", Toast.LENGTH_SHORT).show();
        saveTasks(taskArray, pref);
        addTask.setText("");
        adapter.notifyDataSetChanged();
    }

    public void deleteDoneTasks(View view) {
        ArrayList<Task> tasksToDelete = new ArrayList<>();
        for (Task task : taskArray) {
            if (task.isDone()) {
                tasksToDelete.add(task);
            }
        }
        taskArray.removeAll(tasksToDelete);
        saveTasks(taskArray, pref);
        Toast.makeText(getApplicationContext(),"Selected tasks deleted", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    public static void saveTasks(ArrayList<Task> taskArray, SharedPreferences pref) {
        SharedPreferences.Editor edit = pref.edit();
        Gson gson = new Gson();
        String s = gson.toJson(taskArray);
        edit.putString("tasks", s);
        edit.apply();
    }

    private void pushNotification(Task task) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setShowBadge(true);
            getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Task Notification")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Your task: " + task.getContent() + " will be started at " + task.getDate().toString().substring(10,16)))
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setChannelId(CHANNEL_ID);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("message", "This is a notification message");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
