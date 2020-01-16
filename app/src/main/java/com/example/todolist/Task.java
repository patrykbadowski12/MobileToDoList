package com.example.todolist;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private String content;
    private Date date;
    private boolean done;

    public Task() {}

    public Task(String content, Date date, boolean done) {
        this.content = content;
        this.date = date;
        this.done = done;
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

    @Override
    public String toString() {
        return "Task{" +
                "content='" + content + '\'' +
                ", date=" + date +
                ", done=" + done +
                '}';
    }
}
