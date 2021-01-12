package com.xinger.mita.rvcalendar.entity;

public class DayInfo {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private Week week;
    private int day;
    private String timeData;

    public String getTimeData() {
        return timeData;
    }

    public void setTimeData(String timeData) {
        this.timeData = timeData;
    }

    @Override
    public String toString() {
        return "DayInfo{" +
                "position=" + position +
                ", week=" + week +
                ", day=" + day +
                '}';
    }
}
