package com.example.niit.Share;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetTimeSystem {
    public static String getTime() {
        Calendar now = Calendar.getInstance();
        String strTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strTimeFormat);
        return simpleDateFormat.format(now.getTime());
    }

    public static long getMili() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getTimeInMillis();
    }
}
