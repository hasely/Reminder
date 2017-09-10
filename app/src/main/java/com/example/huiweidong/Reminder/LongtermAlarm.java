package com.example.huiweidong.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class LongtermAlarm extends Service {

    Cursor c;
    long triggerAtTime;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG", "executed at " + new Date().
                        toString());
            }
        }).start();

//immer um 6:00 morgens soll das System diese action dudrchführen
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int intervalOneMin = 10 * 60 * 1000;   // Interval: jede Minute


        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE); //1. get AlarmManager Object

        triggerAtTime = calendar.getTimeInMillis();

        intent = new Intent("alarmmanager.pe.unistuttgart.de.alarmmanagerandnotification.datenbankCheck.ACTION");

        PendingIntent operation = PendingIntent.getBroadcast(this, 0, //定义闹钟响的时候的动作
                intent, PendingIntent.FLAG_CANCEL_CURRENT); //PendingIntent.FLAG_UPDATE_CURRENT

        // getRandomDate();

        am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, intervalOneMin, operation);
        //2.设置闹钟在指定时间提醒（闹钟在睡眠状态下不可用, 系统现在的时间3s之后提醒，


        // Toast.makeText(LongtermAlarm.this, "应该转到notification", Toast.LENGTH_LONG).show();

        return super.onStartCommand(intent, flags, startId);

    }

}
