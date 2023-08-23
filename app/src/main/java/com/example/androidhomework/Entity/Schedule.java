package com.example.androidhomework.Entity;

import com.example.androidhomework.Utils.IdUtil;

public class Schedule {

    private String name;
    private String date;
    private Integer id;


    public Schedule(){

    }

    public Schedule(String name, String date) throws Exception {
        this.name = name;
        this.date = date;
        this.id = IdUtil.differentId(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
