package me.pfrison.polytimeima4.android;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;

import me.pfrison.polytimeima4.Achievements.Achievement;
import me.pfrison.polytimeima4.Achievements.AchievementPopup;
import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.style.Style;
import me.pfrison.polytimeima4.graphics.style.StyleColorArrayAdapter;
import me.pfrison.polytimeima4.graphics.style.StyleVersionArrayAdapter;

public class StylesActivity extends AppCompatActivity {

    private int colorSelected;
    private int versionSelected;

    private int initialColor;
    private int initialVersion;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Style.getThemeResIdFromPreferences(this));
        setContentView(R.layout.activity_styles);

        // achievements
        AchievementPopup achievementPopup = new AchievementPopup(findViewById(R.id.achievements_popup_root), this);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        // get style preference
        colorSelected = pref.getInt(Style.colorKey, 0);
        versionSelected = pref.getInt(Style.versionKey, 0);

        // record is something changed
        initialColor = colorSelected;
        initialVersion = versionSelected;

        // achievements for colors
        if(colorSelected == Style.YELLOW)
            Achievement.achievements[Achievement.ID_YELLOW].setDone(achievementPopup);
        if(colorSelected == Style.MAGENTA)
            Achievement.achievements[Achievement.ID_GANG].setDone(achievementPopup);
        if(colorSelected == Style.CYAN && versionSelected == Style.DARK)
            Achievement.achievements[Achievement.ID_FAV_STYLE].setDone(achievementPopup);

        // Color spinner
        Spinner spinnerColor = findViewById(R.id.style_color_spinner);
        StyleColorArrayAdapter colorAdapter = new StyleColorArrayAdapter(this, R.layout.spinner_style_color, Arrays.asList(Style.STYLE_COLOR_LIST));
        spinnerColor.setAdapter(colorAdapter);
        spinnerColor.setSelection(colorSelected);

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                colorSelected = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Version spinner
        Spinner spinnerVersion = findViewById(R.id.style_version_spinner);
        StyleVersionArrayAdapter versionAdapter = new StyleVersionArrayAdapter(this, R.layout.spinner_style_version, Arrays.asList(Style.STYLE_VERSION_LIST));
        spinnerVersion.setAdapter(versionAdapter);
        spinnerVersion.setSelection(versionSelected);

        spinnerVersion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                versionSelected = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Apply button
        Button apply = findViewById(R.id.style_apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // apply style only if the user changed it
                if(initialColor == colorSelected && initialVersion == versionSelected)
                    return;

                // save app theme
                pref.edit()
                        .putInt(Style.colorKey, colorSelected)
                        .putInt(Style.versionKey, versionSelected)
                        .apply();

                // refresh
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public void finish() {
        // get the applied style
        int colorApplied = pref.getInt(Style.colorKey, 0);
        int versionApplied = pref.getInt(Style.versionKey, 0);

        int resultCode = initialColor != colorApplied || initialVersion != versionApplied ? RESULT_OK : RESULT_CANCELED;
        setResult(resultCode, new Intent());
        super.finish();
    }
}
