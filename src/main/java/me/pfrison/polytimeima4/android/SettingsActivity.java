package me.pfrison.polytimeima4.android;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.style.Style;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Style.getThemeResIdFromPreferences(this));
        setTitle(getResources().getString(R.string.main_menu_settings));

        // fix style
        // action bar
        Drawable background;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            background = new ColorDrawable(getResources().getColor(Style.getColorPrimaryResIdFromPreferences(this), null));
        else
            background = new ColorDrawable(getResources().getColor(Style.getColorPrimaryResIdFromPreferences(this)));
        getSupportActionBar().setBackgroundDrawable(background);

        // status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            int color;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
                color = getResources().getColor(Style.getColorPrimaryDarkResIdFromPreferences(this), null);
            else
                color = getResources().getColor(Style.getColorPrimaryDarkResIdFromPreferences(this));
            window.setStatusBarColor(color);
        }

        setContentView(R.layout.activity_settings);
    }
}
