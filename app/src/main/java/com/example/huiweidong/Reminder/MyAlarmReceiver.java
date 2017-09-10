package com.example.huiweidong.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class MyAlarmReceiver extends BroadcastReceiver {


    public static final String ACTION_ALARM = "alarmmanager.pe.unistuttgart.de.alarmmanagerandnotification.datenbankCheck.ACTION";

    public static final int NOTIFICATION_ID = 2;

    public static String newRadomDate;
    NewMessageNotification notification;

    Cursor cursor;

    int id;
    String name;

    public static boolean compareDate(String dateOfDay) {
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        cursor = Database.getInstance(context).radomDateQuery();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            // if there is a call termin, then notification with the name who should be called
            id = cursor.getInt(0);
            name = cursor.getString(1);// get name
            notification = new NewMessageNotification();
            NewMessageNotification.notify(context, name + " heute anzurufen", 1);


            //set the old radom date als start date for next reminder
            /*try {
                newRadomDate = RadomDate.newRadomDate(cursor.getString(5),cursor.getString(3),cursor.getInt(4));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Database.getInstance(context).updateStartAndRadomDate(id, DateOfDay.getDateOfDay(),newRadomDate);
*/
        }

    }

}
