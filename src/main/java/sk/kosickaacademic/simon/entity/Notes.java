package sk.kosickaacademic.simon.entity;

import java.util.Date;

public class Notes {
    String date;
    String title, task;
    int priority;
    double price;
    boolean done;

    public Notes(String date, String title, String task, int priority, double price, boolean done) {
        this.date = date;
        this.title = title;
        this.task = task;
        this.priority = priority;
        this.price = price;
        this.done = done;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getTask() {
        return task;
    }

    public int getPriority() {
        return priority;
    }

    public double getPrice() {
        return price;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString(){
        return "Date: " +date +" Title: " +title +" Task: " +task +" Priority: " +priority
                +" Price: " +price +" Done: " +done;
    }
}
