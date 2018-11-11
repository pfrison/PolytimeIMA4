package me.pfrison.polytimeima4.android;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.pfrison.polytimeima4.achievements.Achievement;
import me.pfrison.polytimeima4.achievements.AchievementPopup;
import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.style.Style;

public class LegendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Style.getThemeResIdFromPreferences(this));
        setTitle(getResources().getString(R.string.main_menu_legend));
        setContentView(R.layout.activity_legend);

        // achievements
        AchievementPopup achievementPopup = new AchievementPopup(findViewById(R.id.achievements_popup_root), this);

        String[] acronyms = getResources().getStringArray(R.array.legend_acronyms);
        String[] fullNames = getResources().getStringArray(R.array.legend_full_names);

        LinearLayout rootLayout = findViewById(R.id.legend_root);

        // dev anti-bullshit
        if(acronyms.length != fullNames.length)
            throw new RuntimeException("Acronyms and full names list should be equal in length !");

        for(int i=0; i<acronyms.length; i++){
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.legend_items_padding));

            TextView acronymTV = new TextView(this);
            acronymTV.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            acronymTV.setGravity(Gravity.CENTER | Gravity.END);
            acronymTV.setTypeface(null, Typeface.BOLD);
            acronymTV.setText(acronyms[i]);
            acronymTV.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.legend_acronyms_padding), 0);
            itemLayout.addView(acronymTV);

            TextView fullNameTV = new TextView(this);
            fullNameTV.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 5));
            fullNameTV.setText(fullNames[i]);
            itemLayout.addView(fullNameTV);

            rootLayout.addView(itemLayout);
        }

        Achievement.achievements[Achievement.ID_LEGEND].setDone(achievementPopup);
    }
}
