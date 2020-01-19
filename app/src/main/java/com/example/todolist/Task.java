package com.example.todolist;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String content;
    private Date date;
    private boolean done;
    private boolean sentNotification;

    public Task() {}

    public Task(String content, Date date, boolean done, boolean sentNotification) {
        this.content = content;
        this.date = date;
        this.done = done;
        this.sentNotification = sentNotification;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isSentNotification() {
        return sentNotification;
    }

    public void setSentNotification(boolean sentNotification) {
        this.sentNotification = sentNotification;
    }

    @Override
    public String toString() {
        return "Task{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", done=" + done +
                '}';
    }
}
