package me.pfrison.polytimeima4.internet;

import java.util.ArrayList;
import java.util.Calendar;

import me.pfrison.polytimeima4.timetable.Day;
import me.pfrison.polytimeima4.timetable.Lesson;
import me.pfrison.polytimeima4.timetable.TimeTable;
import me.pfrison.polytimeima4.timetable.Week;
import me.pfrison.polytimeima4.utils.Util;

public class TimeTableParser {
    public static TimeTable parseTimeTable(String timeTableStr){
        long currentWeekMondayTime = -1;
        int currentDayDate = -1;

        ArrayList<Lesson> lessonsToAdd = new ArrayList<>();
        ArrayList<Day> daysToAdd = new ArrayList<>();
        ArrayList<Week> weeksToAdd = new ArrayList<>();
        for(String line : timeTableStr.split("\n")){
            // check if line is null or contain nothing
            if(line == null || line.equals(""))
                continue;

            // strip information
            String[] splitLine = line.split("-");

            // ---- Week
            // pattern : SEM-mondayTime
            if(splitLine[0].equalsIgnoreCase("SEM")){
                long prevWeekMondayTime = currentWeekMondayTime;
                currentWeekMondayTime = Long.parseLong(splitLine[1]);
                if(prevWeekMondayTime == -1)
                    continue;

                // build lessons
                Lesson[] lessons = lessonsToAdd.toArray(new Lesson[lessonsToAdd.size()]);
                lessonsToAdd = new ArrayList<>();
                // build days
                daysToAdd = parseDay(daysToAdd, lessons, prevWeekMondayTime, currentDayDate, 0);
                daysToAdd = addBlankDays(daysToAdd, 6 - daysToAdd.size(), prevWeekMondayTime, currentDayDate);
                Day[] days = daysToAdd.toArray(new Day[daysToAdd.size()]);
                daysToAdd = new ArrayList<>();
                // reset currentDayDate
                currentDayDate = -1;

                // add a week
                weeksToAdd.add(new Week(prevWeekMondayTime, days));
            }

            // ---- Day
            // pattern : JOU-day
            else if(splitLine[0].equalsIgnoreCase("JOU")){
                int prevDayDate = currentDayDate;
                currentDayDate = Integer.parseInt(splitLine[1]);
                if(prevDayDate == -1)
                    continue;

                // build lessons
                Lesson[] lessons = lessonsToAdd.toArray(new Lesson[lessonsToAdd.size()]);
                lessonsToAdd = new ArrayList<>();
                // add one or multiple days (in case of empty days)
                daysToAdd = parseDay(daysToAdd, lessons, currentWeekMondayTime, prevDayDate, currentDayDate);
            }

            // ---- Lesson
            // pattern : begin-end-name-room
            else
                lessonsToAdd.add(parseLesson(splitLine));
        }

        // ---- add all lessons and days o the last week
        // build lessons
        Lesson[] lessons = lessonsToAdd.toArray(new Lesson[lessonsToAdd.size()]);
        // build days
        daysToAdd = parseDay(daysToAdd, lessons, currentWeekMondayTime, currentDayDate, 0);
        daysToAdd = addBlankDays(daysToAdd, 6 - daysToAdd.size(), currentWeekMondayTime, currentDayDate);
        Day[] days = daysToAdd.toArray(new Day[daysToAdd.size()]);
        // add a week
        weeksToAdd.add(new Week(currentWeekMondayTime, days));

        // transform arraylist to array
        Week[] weeks = weeksToAdd.toArray(new Week[weeksToAdd.size()]);
        return new TimeTable(weeks);
    }

    private static Lesson parseLesson(String[] splitLine){
        int begin = Integer.parseInt(splitLine[0]);
        int end = Integer.parseInt(splitLine[1]);

        String name;
        if(splitLine.length > 2)
            name = splitLine[2];
        else
            name = "";

        String room;
        if(splitLine.length > 3)
            room = splitLine[3];
        else
            room = "";
        return new Lesson(name, room, begin, end);
    }

    private static ArrayList<Day> parseDay(ArrayList<Day> daysToAdd, Lesson[] lessons, long currentWeekMondayTime, int dayToAddDate, int nextDayDate){
        // ---- add the day
        // build date
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(currentWeekMondayTime);
        date.add(Calendar.DAY_OF_MONTH, dayToAddDate);
        String dateStr = Util.calendarToString(date);
        // new day
        daysToAdd.add(new Day(dateStr, lessons));

        // ---- is there empty days to add ?
        if(nextDayDate - dayToAddDate >= 2){
            int emptyDayToAdd = nextDayDate - dayToAddDate - 1;
            for(int i=0; i<emptyDayToAdd; i++){
                // build date
                date.add(Calendar.DAY_OF_MONTH, 1);
                dateStr = Util.calendarToString(date);
                // new empty day
                daysToAdd.add(new Day(dateStr, new Lesson[0]));
            }
        }
        return daysToAdd;
    }

    private static ArrayList<Day> addBlankDays(ArrayList<Day> daysToAdd, int n, long currentWeekMondayTime, int firstDayDate){
        // build date
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(currentWeekMondayTime);
        date.add(Calendar.DAY_OF_MONTH, firstDayDate);
        // add n empty days
        for(int i=0; i<n; i++){
            // build date
            date.add(Calendar.DAY_OF_MONTH, 1);
            String dateStr = Util.calendarToString(date);
            // new empty day
            daysToAdd.add(new Day(dateStr, new Lesson[0]));
        }
        return daysToAdd;
    }
}
