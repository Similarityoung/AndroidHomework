package com.example.androidhomework.Dao;

import android.graphics.Color;

import com.example.androidhomework.Entity.Schedule;
import com.example.androidhomework.Utils.JsonUtil;
import com.example.androidhomework.Utils.dateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDao {

    private List<Schedule> schedules;

    private static ScheduleDao single = null;

    private static final String SCHEDULE_FILE = "schedule.json";

    public ScheduleDao() { }

//    单例
    public static ScheduleDao getInstance() {
        if (single == null) {
            single = new ScheduleDao();
        }
        return single;
    }

    public List getSchedules() throws Exception {

        schedules = JsonUtil.readJsonToArray(SCHEDULE_FILE,Schedule.class);
        return schedules;
    }

    public void add(Schedule schedule) throws Exception {

        schedules = JsonUtil.readJsonToArray(SCHEDULE_FILE,Schedule.class);
        if(schedules == null){
            schedules = new ArrayList<>();
        }
        int i = getPosition(schedule);

        schedules.add(i,schedule);

        JsonUtil.writeJson(schedules,SCHEDULE_FILE);

    }

//    public void update(Schedule schedule,int position) throws Exception {
//
//        schedules = JsonUtil.readJsonToArray(SCHEDULE_FILE,Schedule.class);
//        if(schedules == null){
//            schedules = new ArrayList<>();
//        }
//        delete(position);
//        add(schedule);
//        JsonUtil.writeJson(schedules,SCHEDULE_FILE);
//
//    }

    public void delete(int position) throws Exception {

        schedules = JsonUtil.readJsonToArray(SCHEDULE_FILE,Schedule.class);
        if(schedules == null){
            schedules = new ArrayList<>();
        }
        schedules.remove(position);
        JsonUtil.writeJson(schedules,SCHEDULE_FILE);

    }

    public int getPosition(Schedule schedule) throws Exception {

        schedules = JsonUtil.readJsonToArray(SCHEDULE_FILE,Schedule.class);
        int i;
        for(i = 0;i < schedules.size();i++){

            if(dateUtils.compareDays(schedule.getDate(),schedules.get(i).getDate())){
                return i;
            }

        }

        return schedules.size();

    }

    public Map<String,Integer> getAllColor() throws Exception {

        Map<String, Integer> colorMap = new HashMap<>();
        schedules = JsonUtil.readJsonToArray(SCHEDULE_FILE,Schedule.class);
        for(Schedule schedule:schedules){
            colorMap.put(dateUtils.changeFormat(schedule.getDate()), Color.parseColor("#f20c00"));
        }

        return colorMap;
    }

    public Map<String,String> getAllName() throws Exception {

        Map<String, String> nameMap = new HashMap<>();
        schedules = JsonUtil.readJsonToArray(SCHEDULE_FILE,Schedule.class);
        for(Schedule schedule:schedules){
            nameMap.put(dateUtils.changeFormat(schedule.getDate()), schedule.getName());
        }

        return nameMap;
    }
}
