package com.example.androidhomework.Utils;

import com.example.androidhomework.Dao.ScheduleDao;
import com.example.androidhomework.Entity.Schedule;

import java.util.List;

public class IdUtil {

    public static Integer differentId( Integer i) throws Exception {

        ScheduleDao scheduleDao = ScheduleDao.getInstance();
        List<Schedule> schedules = scheduleDao.getSchedules();

        for(Schedule schedule:schedules){
            if(schedule.getId() == i){
                i++;
                differentId(i);
            }
        }

        return i;

    }

}
