package me.pfrison.polytimeima4.android;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.MainPanelManager;
import me.pfrison.polytimeima4.graphics.style.Style;
import me.pfrison.polytimeima4.timetable.Day;
import me.pfrison.polytimeima4.timetable.TimeTable;
import me.pfrison.polytimeima4.utils.TimeTableUtil;

// ------------------------ WORK IN PROGRESS -------------------------
public class DayWidgetVertical extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        TimeTable timeTable = MainPanelManager.timeTable;
        RemoteViews remoteViews;
        if(timeTable == null){
            int resId = Style.getWidgetFailFromPreferences(context);
            remoteViews = new RemoteViews(context.getPackageName(), resId);
        }else{
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.day_widget_download_fail_dark);
            Day day = TimeTableUtil.getCurrentDay(timeTable);
        }


        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, DayWidgetVertical.class), remoteViews);
    }
}

