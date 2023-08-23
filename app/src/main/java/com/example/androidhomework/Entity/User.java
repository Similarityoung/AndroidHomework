package com.example.androidhomework.Entity;

import java.util.ArrayList;

public class User {

    private String name;
    private String id;
    private String college;
    private String telephone;

    public User(){

    }

    public User(String name, String id, String college, String telephone) {
        this.name = name;
        this.id = id;
        this.college = college;
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
