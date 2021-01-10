package com.xinger.mita.rvcalendar.utils;

import android.util.Log;

import com.xinger.mita.rvcalendar.entity.DayInfo;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarProxy {
    public static final String TAG = "CalendarProxy";
    private Calendar calendar;
    private int calendarYear;

    public int getCalendarYear() {
        return calendarYear;
    }

    public int getCalendarMonth() {
        return calendarMonth;
    }

    public int getCalendarDay() {
        return calendarDay;
    }

    private int calendarMonth;
    private int calendarDay;

    public CalendarProxy() {
        calendar = Calendar.getInstance();
        calendarYear = calendar.get(Calendar.YEAR);
        calendarMonth = calendar.get(Calendar.MONTH) + 1;
        calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(calendarYear, calendarMonth, calendarDay);
        Log.e(TAG, "year " + calendarYear + "month " + calendarMonth + "day" + calendarDay);
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


    /**
     * 获取某年某月的天数合计，和 日/一..../六对应上
     * isShowLastAndNextDay 是否显示上个月的尾数，和下个月的天数
     *
     * @return
     */
    public List<DayInfo> getDay4YearMonth(int year, int month, boolean isShowLastAndNextDay) {
        calendar.set(year, month - 1, 1);
        calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int monthDays = calendar.get(Calendar.DATE); //当前年月天数
        Log.e(TAG, "monthDays " + monthDays);
        calendar.set(year, month - 1, 0);
        int firstDay4week = calendar.get(Calendar.DAY_OF_WEEK);//当前月 第一天是周几
        Log.e(TAG, "firstDay4week " + firstDay4week);
        List<DayInfo> dayInfos = new ArrayList<>();
        if (firstDay4week <= 6) {
            for (int i = 0; i < firstDay4week; i++) {
                DayInfo dayInfo = new DayInfo();
                dayInfo.setDay(0);
                dayInfos.add(dayInfo);
            }
        }
        for (int i = 1; i <= monthDays; i++) {
            DayInfo dayInfo = new DayInfo();
            dayInfo.setDay(i);
            dayInfos.add(dayInfo);
        }
        return dayInfos;
    }

//    public List<DayInfo> getDay4Week(int year, int month, int day) {
//        calendar.set(year, month - 1, day - 1);
//        int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        Log.e(TAG, "getDay4Week " + currentWeek);
//        List<DayInfo> dayInfos = new ArrayList<>();
//        for (int i = 0; i <= 6; i++) {
//            int i1 = day - currentWeek;
//            DayInfo dayInfo = new DayInfo();
//            dayInfo.setDay(i1);
//
//        }
//        return null;
//    }


}
