package me.pfrison.polytimeima4.achievements;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.pfrison.polytimeima4.R;

public class Achievement {
    // Hello you =D Did you came to get the secret achievement ? There you go :
    public static final String secretAchievementPassword = "LOLTropSimple";

    public static Achievement[] achievements;

    public static final int ID_BEGIN = 0;
    public static final int ID_LEGEND = 1;
    public static final int ID_KNOWLEDGE = 2;
    public static final int ID_YELLOW = 3;
    public static final int ID_GANG = 4;
    public static final int ID_FAV_STYLE = 5;
    public static final int ID_BORING = 6;
    public static final int ID_MAD = 7;
    private static final int ID_BREAK = 8;
    public static final int ID_CLUB = 9;
    public static final int ID_BELGE = 10;
    public static final int ID_CLICKER = 11;
    public static final int ID_CLICKER_BATTERY = 12;
    public static final int ID_CLICKER_FREEZER = 13;
    public static final int ID_CLICKER_EARTH = 14;
    public static final int ID_CLICKER_NEVERCLICK = 15;

    private static final int NUMBER_OF_ACHIEVEMENTS = 16;

    private static final String achievementDoneKey = "achievementsDone";

    private static int[] IMG_LIST = new int[]{
            R.mipmap.begin,
            R.mipmap.legend,
            R.mipmap.knowledge,
            R.mipmap.jaune,
            R.mipmap.gang,
            R.mipmap.favstyle,
            R.mipmap.boring,
            R.mipmap.mad,
            R.mipmap.breaks,
            R.mipmap.crep,
            R.mipmap.belge,
            R.mipmap.clicker,
            R.mipmap.clicker_battery,
            R.mipmap.clicker_freezer,
            R.mipmap.clicker_earth,
            R.mipmap.clicker_nerverclick
    };

    private int id;
    public String title;
    public String description;
    public int imageResource;
    private boolean done;

    public Achievement(int id, String title, String description, int imageResource, boolean done){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
        this.done = done;
    }

    public int getId(){return id;}
    public boolean isDone(){return done;}
    public void setDone(AchievementPopup popup){
        if(this.done)
            return;

        // I told you : you CAN'T have this achievement
        if(id == ID_BREAK)
            return;

        this.done = true;
        popup.appear(this);
        saveAchievements(popup.getContext());
    }

    public static void listAchievements(Context context){
        achievements = new Achievement[NUMBER_OF_ACHIEVEMENTS];

        // get done achievements
        int dones = PreferenceManager.getDefaultSharedPreferences(context).getInt(achievementDoneKey, 0);
        String donesStr = Integer.toBinaryString(dones);

        // get name and description achievement string
        String[] arrayName = context.getResources().getStringArray(R.array.achievement_name);
        String[] arrayDesc = context.getResources().getStringArray(R.array.achievement_desc);

        // developer bullshit guard
        if(arrayName.length != NUMBER_OF_ACHIEVEMENTS)
            throw new RuntimeException("Something is wrong with arrayName or NUMBER_OF_ACHIEVEMENTS, please fix it !");
        if(arrayDesc.length != NUMBER_OF_ACHIEVEMENTS)
            throw new RuntimeException("Something is wrong with arrayDesc or NUMBER_OF_ACHIEVEMENTS, please fix it !");
        if(IMG_LIST.length != NUMBER_OF_ACHIEVEMENTS)
            throw new RuntimeException("Something is wrong with IMG_LIST or NUMBER_OF_ACHIEVEMENTS, please fix it !");

        // for every achievements
        for(int i=0; i<NUMBER_OF_ACHIEVEMENTS; i++){
            if(donesStr.length() > i){
                boolean done = donesStr.charAt(i) == '1';
                achievements[i] = new Achievement(i, arrayName[i], arrayDesc[i], IMG_LIST[i], done);
            }else
                achievements[i] = new Achievement(i, arrayName[i], arrayDesc[i], IMG_LIST[i], false);
        }
    }

    private static void saveAchievements(Context context){
        StringBuilder sb = new StringBuilder();
        for(Achievement achievement : achievements){
            char c = achievement.done ? '1' : '0';
            sb.append(c);
        }
        int dones = Integer.parseInt(sb.toString(), 2);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putInt(achievementDoneKey, dones).apply();
    }

    public static int countAchievementDone(){
        int count = 0;
        for(Achievement achievement : achievements)
            if(achievement.isDone()) count++;
        return count;
    }
}
