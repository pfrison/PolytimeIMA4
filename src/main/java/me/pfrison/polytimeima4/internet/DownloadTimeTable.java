package me.pfrison.polytimeima4.internet;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.android.MainActivity;
import me.pfrison.polytimeima4.graphics.MainPanelManager;
import me.pfrison.polytimeima4.graphics.MenuUpdater;
import me.pfrison.polytimeima4.timetable.TimeTable;

public class DownloadTimeTable extends AsyncTask<Integer, Void, String> {
    private MainActivity activity;
    private Menu menu;
    private int groupId;

    public DownloadTimeTable(MainActivity activity, Menu menu){
        this.activity = activity;
        this.menu = menu;
    }

    @Override
    protected String doInBackground(Integer... groupIds) {
        // verify connection
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
        if(!isWifiOn() && pref.getBoolean("useWifi", false))
            return null;

        this.groupId = groupIds[0];
        String url = "http://serveur24sur24.free.fr/Polytime/TP" + String.valueOf(groupIds[0]+1) + ".cal";
        String timeTableStr = null;
        try {
            // open connection
            URL myUrl = new URL(url);
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(3000);
            connection.connect();

            // read content
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String inputLine;
            while((inputLine = reader.readLine()) != null)
                stringBuilder.append(inputLine).append("\n");
            timeTableStr = stringBuilder.toString();

            // close connection
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeTableStr;
    }

    protected void onPostExecute(String timeTableStr) {
        if(timeTableStr == null) {
            MainPanelManager.printMessage(activity.getResources().getString(R.string.message_download_fail), MainPanelManager.KEEP, activity);
            MenuUpdater.setOffline(menu, activity);
            return;
        }

        TimeTableSaverLoader.saveTimeTable(timeTableStr, groupId, activity);
        TimeTable timeTable = null;
        try{
            timeTable = TimeTableParser.parseTimeTable(timeTableStr);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(timeTable == null){
            MainPanelManager.printMessage(activity.getResources().getString(R.string.message_parsing_fail), MainPanelManager.REPLACE, activity);
            MenuUpdater.setReadError(menu, activity);
            return;
        }

        MainPanelManager.setTimeTable(timeTable, activity, MainPanelManager.DOWNLOAD);
        MenuUpdater.setOnline(menu, activity);
    }

    private boolean isWifiOn(){
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Network[] networks = connMgr.getAllNetworks();
                for (Network mNetwork : networks) {
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(mNetwork);
                    if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                        return true;
                }
            }else{
                NetworkInfo[] info = connMgr.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo networkInfo : info) {
                        if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
