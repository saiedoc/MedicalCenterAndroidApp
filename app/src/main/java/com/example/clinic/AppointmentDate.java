package com.example.clinic;

public class AppointmentDate {

    private int day;
    private Month month;
    private AMPM ampm;
    private int hour;
    private int minutes;

    public AppointmentDate(int day, Month month, int hour, int minutes, AMPM ampm) {
        this.day = day;
        this.month = month;
        this.ampm = ampm;
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public AMPM getAMPM() {
        return ampm;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }
}
