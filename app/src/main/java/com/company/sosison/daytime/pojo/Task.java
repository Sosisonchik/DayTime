package com.company.sosison.daytime.pojo;

public class Task {
    String text;
    String date;
    String mark;
    boolean check;

    public Task(String text, String date, String mark, boolean check) {
        this.text = text;
        this.date = date;
        this.check = check;
        this.mark = mark;
    }

    public String getText() {
        return text;
    }

    public String getMark() {
        return mark;
    }

    public String getDate() {
        return date;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
