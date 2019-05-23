package com.example.niit.Share;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FormatTime {
    @SuppressLint("SimpleDateFormat")
    public static String formattedDate(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        long mili = date.getTime() + 25200000;

        Log.d("format", "format: " + mili);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mili);

        return new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String formattedTimeMinute(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        assert date != null;
        long mili = date.getTime() + 25200000;

        Log.d("format", "format: " + mili);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mili);

        return new SimpleDateFormat("HH:mm").format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String formattedTimeSecond(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        long mili = date.getTime() + 25200000;

        Log.d("format", "format: " + mili);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mili);

        return new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());
    }
}
