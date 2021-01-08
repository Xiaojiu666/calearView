package com.xinger.mita.rvcalendar.utils;

import android.util.Log;

import java.util.Calendar;

public class CalendarProxy {
    public static final String TAG = "CalendarProxy";
    private Calendar calendar;
    private int calendarYear;
    private int calendarMonth;
    private int calendarDay;

    public CalendarProxy() {
        calendar = Calendar.getInstance();
        calendarYear = calendar.get(Calendar.YEAR);
        calendarMonth = calendar.get(Calendar.MONTH);
        calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.set(2020, 11, calendarDay);
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int empty = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e(TAG, "year " + calendarYear + "month " + calendarMonth + "day" + calendarDay);
        Log.e(TAG, "maxDayOfMonth " + maxDayOfMonth + "empty " + empty);
    }

    public String getDateFormat(CalendarDateType calendarDateType) {
        StringBuffer date = new StringBuffer();
        switch (calendarDateType) {
            case DATE_Y_M:
                date.append(calendarYear);
                date.append("年");
                date.append(calendarMonth + 1);
                date.append("月");
                break;
        }
        return date.toString();
    }
}
