package com.example.todolist;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String content;
    private Date date;
    private boolean done;
    private boolean sendedNotification;

    public Task() {}

    public Task(String content, Date date, boolean done, boolean sendedNotification) {
        this.content = content;
        this.date = date;
        this.done = done;
        this.sendedNotification = sendedNotification;
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

    public boolean isSendedNotification() {
        return sendedNotification;
    }

    public void setSendedNotification(boolean sendedNotification) {
        this.sendedNotification = sendedNotification;
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
