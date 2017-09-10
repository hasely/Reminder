package com.example.huiweidong.Reminder;

import java.util.Calendar;

/**
 *
 * get the current date
 */

public class DateOfDay {
    public static String dateOfDay;


    public static String getDateOfDay() {
        Calendar calendar = Calendar.getInstance();
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd.MM.yyyy");
        return dateOfDay = format.format(calendar.getTime());
    }


}
