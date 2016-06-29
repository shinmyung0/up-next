package com.example.shinythings.thingstodo;

import android.graphics.Color;

import java.util.Date;

/**
 * Created by shiyoon on 6/19/16.
 */
public class TodoItem {

    private String todo;

    private String bgColor;
    // TODO: implement swipe to cross out
    public static final String STATUS_DONE = "done";
    public static final String STATUS_TODO = "todo";
    private String status;

    private int order;

    private long id;

    // TODO: implement paging
    private int page;
    private Date until;



    public TodoItem(String content, String color, long id) {
        this(content, color, "todo", 0, id);
    }


    public TodoItem(String content, String color, String status, int order, long id) {
        this.todo = content;
        this.bgColor = color;
        this.status = status;
        this.order = order;
        this.id = id;
    }






    public String getTodo() {
        return todo;
    }

    public void setTodo(String txt) {
        todo = txt;
    }


    public Date getUntil() {
        return until;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int o) {
        order = o;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int p) {
        page = p;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
