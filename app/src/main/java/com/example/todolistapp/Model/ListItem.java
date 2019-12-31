package com.example.todolistapp.Model;

public class ListItem {
    private boolean cheked;
    private String task;

    public ListItem(boolean cheked, String task) {
        this.cheked = cheked;
        this.task = task;
    }

    public boolean isCheked() {
        return cheked;
    }

    public void setCheked(boolean cheked) {
        this.cheked = cheked;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
