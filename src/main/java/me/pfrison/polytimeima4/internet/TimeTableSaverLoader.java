package me.pfrison.polytimeima4.internet;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TimeTableSaverLoader {
    static void saveTimeTable(String timeTableStr, int groupId, Context context){
        String fileName = "timetable" + String.valueOf(groupId) + ".cal";
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(timeTableStr.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadTimeTable(int groupId, Context context){
        String fileName = "timetable" + String.valueOf(groupId) + ".cal";
        String timeTableStr = null;
        try {
            FileInputStream in = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line).append("\n");
            timeTableStr = sb.toString();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeTableStr;
    }
}
