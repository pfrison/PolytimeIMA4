package me.pfrison.polytimeima4.timetable;

public class Lesson {
    public String name;
    public String room;
    public int begin;
    public int end;

    public Lesson(String name, String room, int begin, int end){
        this.name = name;
        this.room = room;
        this.begin = begin;
        this.end = end;
    }
}
