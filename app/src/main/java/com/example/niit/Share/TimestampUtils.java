package com.example.niit.Share;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimestampUtils {
    public static DateFormat getISO8601StringForCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateFormat.format(new Date());
        return dateFormat;
    }

    public static DateFormat getISO8601StringToDate(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.UK);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat;
    }
}
