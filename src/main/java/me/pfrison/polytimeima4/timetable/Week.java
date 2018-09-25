package me.pfrison.polytimeima4.timetable;

import java.util.Calendar;

public class Week {
    public Calendar monday_time;
    public Day[] days;

    public Week(long monday_time, Day[] days){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(monday_time);
        this.monday_time = cal;
        this.days = days;
    }
}
