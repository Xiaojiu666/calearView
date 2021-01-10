package com.xinger.mita.rvcalendar.entity;

public enum Week {
    SUNDAY("日"), MONDAY("一"), TUESDAY("二"), WEDNESDAY("三"), THURSDAY("四"),
    FRIDAY("五"), SATURDAY("六");
    public String name;

    Week(String name) {
        this.name = name;
    }
}
