package com.example.androidhomework.Dao;

import com.example.androidhomework.Entity.Schedule;
import com.example.androidhomework.Entity.SimpleNEUClass;
import com.example.androidhomework.Utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class SimpleNEUClassDao {

    private List<SimpleNEUClass> classArray;
    private static SimpleNEUClassDao single = null;

    private static final String CLASS_FILE = "class.json";

    public SimpleNEUClassDao () {

    }

    public static SimpleNEUClassDao getInstance() {
        if (single == null) {
            single = new SimpleNEUClassDao();
        }
        return single;
    }

    public List getClassArray() throws Exception {
        classArray = JsonUtil.readJsonToArray(CLASS_FILE, SimpleNEUClass.class);
        return classArray;
    }

    public void setClassArray(List<SimpleNEUClass> classArray) throws Exception {
        JsonUtil.writeJson(classArray,CLASS_FILE);
    }

    public List selectClassArray(int week,int day) throws Exception {

        classArray = JsonUtil.readJsonToArray(CLASS_FILE, SimpleNEUClass.class);

        List<SimpleNEUClass> selectArray = new ArrayList<>();

        for(SimpleNEUClass simpleNEUClass:classArray){

            if(simpleNEUClass.getDay().equals(day)){

                for(Integer weeks:simpleNEUClass.getWeeks()){
                    if(weeks.equals(week)){
                        selectArray.add(simpleNEUClass);
                    }
                }

            }

        }

        return selectArray;

    }



}
