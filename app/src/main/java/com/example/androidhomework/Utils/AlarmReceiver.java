package com.example.androidhomework.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.androidhomework.Activity.MainActivity;
import com.example.androidhomework.Dao.ScheduleDao;
import com.example.androidhomework.Entity.Schedule;
import com.example.androidhomework.R;


public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1000;
    private ScheduleDao scheduleDao;
    private String name;
    private String date;

    @Override
    public void onReceive(Context context, Intent intent) {

        scheduleDao = ScheduleDao.getInstance();
        try {
            Schedule schedule = (Schedule) scheduleDao.getSchedules().get(0);
            name = schedule.getName();
            date = schedule.getDate();
            scheduleDao.delete(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);
        Notification notification = new NotificationCompat.Builder(context,"wang")
                .setTicker("日程提醒")
                .setContentTitle("日程 "+ name +" " + date)
                .setContentText("吉时已到，开始干活")
                //通知图标应该使用纯色的图片，因为Android从5.0系统开始，对于图标设计进行了修改，所有应用程序
                //的通知栏图标，应该使用alpha图层，其实就是没有颜色的图片
                .setSmallIcon(android.R.drawable.btn_star)
                //给小图标设置颜色
                .setColor(Color.parseColor("#ff0000"))
                //点击通知时跳转
                .setContentIntent(pendingIntent)
                //设置点击通知后清除通知
                .setAutoCancel(true)
                //设置通知被创建的时间  不设置的话就显示的是当前系统时间
                // .setWhen()
                .build();
        manager.notify(NOTIFICATION_ID, notification);


    }

}