package me.pfrison.polytimeima4.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import me.pfrison.polytimeima4.Achievements.Achievement;
import me.pfrison.polytimeima4.Achievements.AchievementPopup;
import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.internet.TimeTableSaverLoader;
import me.pfrison.polytimeima4.graphics.MainPanelManager;
import me.pfrison.polytimeima4.graphics.MenuUpdater;
import me.pfrison.polytimeima4.graphics.style.Style;
import me.pfrison.polytimeima4.internet.DownloadTimeTable;
import me.pfrison.polytimeima4.internet.TimeTableParser;
import me.pfrison.polytimeima4.timetable.Lesson;
import me.pfrison.polytimeima4.timetable.TimeTable;
import me.pfrison.polytimeima4.utils.TimeTableUtil;

/**
 * TODO list :
 *  - ??? error handler (button "FIX IT !")
 *  - ??? change app color in "recent app" according to actual theme
 */

public class MainActivity extends AppCompatActivity {
    private AchievementPopup achievementPopup;
    private SharedPreferences pref;
    private Menu menu;
    private int groupId;

    private boolean canDetectAchievements;
    private int madClicks = 0;
    private boolean madContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Style.getThemeResIdFromPreferences(this));
        setContentView(R.layout.activity_main);

        // set default settings
        PreferenceManager.setDefaultValues(this, R.xml.settings_fragment, false);

        // achievements
        achievementPopup = new AchievementPopup(findViewById(R.id.achievements_popup_root), this);

        // Loading message
        MainPanelManager.printMessage(getResources().getString(R.string.message_loading), MainPanelManager.REPLACE, this);

        // get preferences
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        groupId = pref.getInt("ctrlPanelSpinner", 0);

        // ------ Spinner
        Spinner ctrlPanelSpinner = findViewById(R.id.ctrl_panel_spinner);
        // set text list
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.group_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ctrlPanelSpinner.setAdapter(adapter);
        // set the remembered selection
        ctrlPanelSpinner.setSelection(groupId);
        // on selection
        ctrlPanelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // remember position
                pref.edit().putInt("ctrlPanelSpinner", position).apply();
                groupId = position;

                // refresh time table
                MenuUpdater.setDownloading(menu, getBaseContext());
                loadAndDownload();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // ------ Buttons
        Button ctrlPanelPrev = findViewById(R.id.ctrl_panel_prev);
        Button ctrlPanelCurrent = findViewById(R.id.ctrl_panel_current);
        Button ctrlPanelNext = findViewById(R.id.ctrl_panel_next);
        // on click
        final Activity activity = this;
        ctrlPanelPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display previous week
                MainPanelManager.prevWeek(activity);
                madClicks++;
            }
        });
        ctrlPanelCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display current week
                MainPanelManager.currentWeek(activity);
                madClicks++;
            }
        });
        ctrlPanelNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // display next week
                MainPanelManager.nextWeek(activity);
                madClicks++;
            }
        });

        // Load (if any) and Download the timetable
        MenuUpdater.setDownloading(menu, this);
        loadAndDownload();

        // can we listen to other achievements ?
        canDetectAchievements = Achievement.achievements[Achievement.ID_BEGIN].isDone();

        // achievement get : begin
        Achievement.achievements[Achievement.ID_BEGIN].setDone(achievementPopup);

        // detect achievement "mad"
        detectAchievementMad();

        // start notification
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.main_menu_legend:
                intent = new Intent(this, LegendActivity.class);
                startActivity(intent);
                return true;
            case R.id.main_menu_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.main_menu_styles:
                intent = new Intent(this, StylesActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.main_menu_achievements:
                intent = new Intent(this, AchievementActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // used to catch the result of styleActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            finish();
            startActivity(getIntent());
        }
    }

    private void loadAndDownload(){
        // ------ Load previously saved timetable if any
        String timeTableStr = TimeTableSaverLoader.loadTimeTable(groupId, this);
        if(timeTableStr != null && !timeTableStr.equals("")) {
            TimeTable timeTable = TimeTableParser.parseTimeTable(timeTableStr);
            MainPanelManager.setTimeTable(timeTable, this, MainPanelManager.LOAD);
        }

        // ------ Start Downloading timetable
        new DownloadTimeTable(this, menu).execute(groupId);
    }

    public void detectAchievementBoring(TimeTable timeTable){
        if(!canDetectAchievements)
            return;

        // detect the middle of the lesson like this :
        // begin - 30% not middle - 40% middle - 60% not middle - end
        Lesson lessonMiddle = TimeTableUtil.getNextLessonMiddle30Percent(timeTable);
        if(lessonMiddle != null)
            Achievement.achievements[Achievement.ID_BORING].setDone(achievementPopup);
    }

    private void detectAchievementMad(){
        madContinue = !Achievement.achievements[Achievement.ID_MAD].isDone();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(madContinue){
                    try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                    if(madClicks >= 10){
                        // run on ui thread so we can touch at views
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Achievement.achievements[Achievement.ID_MAD].setDone(achievementPopup);
                            }
                        });
                        madContinue = false;
                    }else
                        madClicks = 0;
                }
            }
        }).start();
    }
}
