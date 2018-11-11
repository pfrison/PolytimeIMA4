package me.pfrison.polytimeima4.android;

import android.app.Application;

import me.pfrison.polytimeima4.achievements.Achievement;

public class PolytimeApplication extends Application {
    public void onCreate(){
        super.onCreate();
        Achievement.listAchievements(this);
    }
}
