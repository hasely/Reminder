package com.example.huiweidong.Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.example.huiweidong.Reminder.com.example.huiweidong.Reminder.Activities.SetTimeActivity.inteval_value;
import static com.example.huiweidong.Reminder.com.example.huiweidong.Reminder.Activities.SetTimeActivity.nr1;


/**
 * Created by HuiweiDong on 21.12.16.
 */

public class RadomDate {

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); // define new date format
    private String randomDate;

    /**
     * set a string date to the wanted format
     *
     * @param sDate: string date
     * @throws ParseException
     */
    private Date setDateFormat(String sDate) {
        Date dDate = null;
        try {
            dDate = dateFormat.parse(sDate); //parse string date into date

        } catch (ParseException e) {
            System.err.println("Fehler von String date to Date date: " + e.getMessage());
            // return null;
        }

        return dDate;

    }

    /**
     * set a string date to the wanted format
     *
     * @param
     * @throws ParseException
     */
    private String setStringDate(Date date) {
        String formatedDate = dateFormat.format(date);

        //calendar.setTime(dDate);
        return formatedDate;
    }

    /**
     * calculate a radom date
     *
     * @param beginnDate
     * @param repeatInteval
     *
     */
    public void setRadomDate(String beginnDate, int repeatInteval, int unsharpenDays) {

        calendar.setTime(setDateFormat(beginnDate));

        if (inteval_value.equals("Month(s)")) {
            calendar.add(Calendar.MONTH, nr1);
        } else if (inteval_value.equals("Week(s)")) {
            calendar.add(Calendar.WEEK_OF_YEAR, nr1);

        }
        Random random = new Random();
        int nr = random.nextInt(unsharpenDays * 2 + 1) - repeatInteval;
        calendar.add(Calendar.DAY_OF_MONTH, nr);

        String tempStringDate = calendar.getTime().toString();
        Date temp = setDateFormat(tempStringDate);


        this.randomDate = setStringDate(temp);
        //this.randomDate=  beginnDate;
    }


    public String getRandomDate() {
        return randomDate;
    }

    public String newRadomDate(String oldRadomDate, String repeatsInteval, int unsharpNr) throws ParseException {

        //set beginnDate als the calendar date for calculating ein radom date
        // Calendar calendar = Calendar.getInstance();
        //java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd.MM.yyyy");

        //Date date = format.parse(oldRadomDate);
        //calendar.setTime(date);

        setDateFormat(oldRadomDate);
        Random random = new Random();
        int nr = 0;
        /*if (repeatsInteval.equals("Month(s)")) {
            calendar.add(Calendar.MONTH, repeatNr1);
        } else if (repeatsInteval.equals("Week(s)")) {
            calendar.add(Calendar.WEEK_OF_YEAR, repeatNr1);

        }

        nr = random.nextInt(unsharpNr * 2 + 1) - unsharpNr;
        calendar.add(Calendar.DAY_OF_MONTH, nr);
        randomDate = format.format(calendar.getTime());
        return randomDate;*/
        return null;
    }
}
