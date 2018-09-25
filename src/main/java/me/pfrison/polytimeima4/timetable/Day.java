package me.pfrison.polytimeima4.timetable;

public class Day {
    public String date;
    public Lesson[] lessons;

    public Day(String date, Lesson[] lessons){
        this.date = date;
        this.lessons = lessons;
    }
}
