package com.example.androidhomework.Utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.androidhomework.Entity.Schedule;

import java.text.ParseException;
import java.util.Date;


public class AlarmTimer {

    public static void setAlarmTimer(Context context, Schedule schedule) throws ParseException {
        Intent myIntent = new Intent((Activity)context,AlarmReceiver.class);
        //传递定时日期
//        myIntent.putExtra("date", schedule.getDate());
//        myIntent.putExtra("name", schedule.getName());
        //给每个闹钟设置不同ID防止覆盖
        int alarmId = schedule.getId();
        PendingIntent sender = PendingIntent.getBroadcast(context, alarmId, myIntent, 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, dateUtils.getDate(schedule.getDate()), sender);
    }

    /**
     * 取消闹钟
     *
     * @param context
     * @param action
     */
    public static void cancelAlarmTimer(Context context, String action, int id) {
        Intent myIntent = new Intent();
        myIntent.setAction(action);
        PendingIntent sender = PendingIntent.getBroadcast(context, id, myIntent,0);
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(sender);
    }
}