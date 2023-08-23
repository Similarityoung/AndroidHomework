package com.example.androidhomework.Dao;

import com.example.androidhomework.Entity.User;
import com.example.androidhomework.Utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private List<User> users;

    private static UserDao single = null;

    private static final String USER_FILE = "user.json";

    public UserDao(){}

    public static UserDao getInstance(){
        if(single == null){
            single = new UserDao();
        }
        return single;
    }

    public User getUser() throws Exception {

        users = JsonUtil.readJsonToArray(USER_FILE,User.class);
        return users.get(0);

    }

    public void setUsers(User user) throws Exception {

        users = new ArrayList<>();
        users.add(user);
        JsonUtil.writeJson(users,USER_FILE);

    }

}
