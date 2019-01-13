package me.pfrison.polytimeima4.utils;

import java.util.Calendar;

import me.pfrison.polytimeima4.timetable.Day;
import me.pfrison.polytimeima4.timetable.Lesson;
import me.pfrison.polytimeima4.timetable.TimeTable;
import me.pfrison.polytimeima4.timetable.Week;

public class TimeTableUtil {
    // current week
    public static int getCurrentWeekInt(TimeTable timeTable, boolean protection){
        if(timeTable == null)
            return -1;

        Calendar today = Calendar.getInstance();
        // add one day --> this will show the next week at sunday
        today.add(Calendar.DAY_OF_YEAR, 1);
        long todayMillis = today.getTimeInMillis();
        int weekToShow = -1;
        for(int i=0; i<timeTable.weeks.length; i++){
            // compare the monday of every week with the current day
            if(timeTable.weeks[i].monday_time.getTimeInMillis() > todayMillis){
                weekToShow = i-1;
                break;
            }
        }
        if(weekToShow == -1 && protection)
            return 0;
        return weekToShow;
    }
    private static Week getCurrentWeek(TimeTable timeTable){
        if(timeTable == null)
            return null;

        int currentWeekInt = getCurrentWeekInt(timeTable, false);
        if(currentWeekInt == -1)
            return null;
        return timeTable.weeks[currentWeekInt];
    }

    // current day
    private static Day getCurrentDay(Week currentWeek){
        if(currentWeek == null)
            return null;

        Calendar today = Calendar.getInstance();
        // DAY_OF_WEEK -> sunday = 1, monday = 2, etc...
        if(today.get(Calendar.DAY_OF_WEEK) == 1)
            return null;
        return currentWeek.days[today.get(Calendar.DAY_OF_WEEK) - 2];
    }
    public static Day getCurrentDay(TimeTable timeTable){
        Week currentWeek = getCurrentWeek(timeTable);
        if(currentWeek == null)
            return null;
        return getCurrentDay(currentWeek);
    }

    // current/next lesson
    public static Lesson getNextLesson30Mins(TimeTable timeTable){
        if(timeTable == null)
            return null;

        Day currentDay = getCurrentDay(timeTable);
        if(currentDay == null)
            return null;

        Calendar today = Calendar.getInstance();
        for(Lesson lesson : currentDay.lessons){
            // next/current if "durationDetectionBegin" minutes before begin and "durationDetectionEnd" minutes before end
            if(lesson.begin * 60 - 30 <= today.get(Calendar.HOUR_OF_DAY) * 60
                    && today.get(Calendar.HOUR_OF_DAY) * 60 < lesson.end * 60 - 30){
                return lesson;
            }
        }
        return null;
    }
    public static Lesson getNextLessonMiddle30Percent(TimeTable timeTable){
        if(timeTable == null)
            return null;

        Day currentDay = getCurrentDay(timeTable);
        if(currentDay == null)
            return null;

        Calendar today = Calendar.getInstance();
        for(Lesson lesson : currentDay.lessons){
            float lenght = (lesson.end - lesson.begin)*60;
            if(lesson.begin * 60 + lenght*0.3f <= today.get(Calendar.HOUR_OF_DAY) * 60
                    && today.get(Calendar.HOUR_OF_DAY) * 60 < lesson.end * 60 - lenght* 0.3f){
                return lesson;
            }
        }
        return null;
    }
}
