package me.pfrison.polytimeima4.android;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.clicker.Clicker;
import me.pfrison.polytimeima4.clicker.ClickerSaverLoader;
import me.pfrison.polytimeima4.clicker.ClickerStatics;
import me.pfrison.polytimeima4.clicker.upgrade.ClickerUpgradeItem;
import me.pfrison.polytimeima4.clicker.upgrade.Upgrade;
import me.pfrison.polytimeima4.graphics.ClickerGraphics;
import me.pfrison.polytimeima4.utils.ClickerUtil;

public class ClickerStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ClickerTheme);
        setTitle(getResources().getString(R.string.clicker_menu_stats));
        setContentView(R.layout.activity_clicker_stats);

        // load save
        Clicker clicker = ClickerSaverLoader.loadClickerInternal(this);

        // display total crepes
        TextView totalMade = findViewById(R.id.clicker_stats_made_crepes);
        totalMade.setText(getResources().getString(R.string.clicker_total_made_crepes) + " "
                + ClickerUtil.humanReadableNumbersCounter(clicker.getMadeCrepes(), this));

        // display total handmade crepes
        TextView totalHandmade = findViewById(R.id.clicker_stats_hand_made_crepes);
        totalHandmade.setText(getResources().getString(R.string.clicker_total_hand_made_crepes) + " "
                + ClickerUtil.humanReadableNumbersCounter(clicker.getHandMadeCrepes(), this));

        // time played
        TextView timePlayed = findViewById(R.id.clicker_stats_time_played);
        timePlayed.setText(getResources().getString(R.string.clicker_time_played) + " "
                + clicker.getHoursPlayed() + " " + getResources().getString(R.string.hours));

        // golden clicked
        TextView goldenClicked = findViewById(R.id.clicker_stats_golden_clicked);
        goldenClicked.setText(getResources().getString(R.string.clicker_golden_clicked) + " "
                + clicker.getGoldenClicked() + " " + getResources().getString(R.string.golden_crepes));

        // ingredients stats
        LinearLayout flavors = findViewById(R.id.clicker_stats_flavors);
        for(int i=0; i<clicker.level; i++)
            flavors.addView(getIngredientItem(i, clicker, this));

        // upgrades bought
        LinearLayout upgrades = findViewById(R.id.clicker_stats_upgrades_bought);
        // flavors
        for(int i=0; i<ClickerStatics.INGREDIENTS_COUNT; i++){
            for(int j=0; j<clicker.getUpgradesFlavorsAt(i); j++){
                ClickerUpgradeItem item = new ClickerUpgradeItem(new Upgrade(Upgrade.INGREDIENT, i, j), this);
                upgrades.addView(item);
            }
        }
        // supplements
        for (int i=0; i<ClickerStatics.SUPPLEMENTS_COUNT; i++){
            if(clicker.getUpgradesSupplementsAt(i)){
                ClickerUpgradeItem item = new ClickerUpgradeItem(new Upgrade(Upgrade.SUPPLEMENT, i), this);
                upgrades.addView(item);
            }
        }
        // gloves
        for(int i=0; i<clicker.getUpgradesGloves(); i++){
            ClickerUpgradeItem item = new ClickerUpgradeItem(new Upgrade(Upgrade.GLOVE, i), this);
            upgrades.addView(item);
        }
    }

    public static View getIngredientItem(int level, Clicker clicker, Context context){
        LinearLayout root = new LinearLayout(context);
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.setOrientation(LinearLayout.HORIZONTAL);

        // icon
        ImageView icon = new ImageView(context);
        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.clicker_item_icon_size);
        icon.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize));
        icon.setImageResource(ClickerGraphics.getIngredientsResId(level));
        root.addView(icon);

        //name, description and prize
        LinearLayout namePrizeLayout = new LinearLayout(context);
        namePrizeLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        namePrizeLayout.setPadding(context.getResources().getDimensionPixelSize(R.dimen.clicker_item_padding), 0, 0, 0);
        namePrizeLayout.setOrientation(LinearLayout.VERTICAL);
        // name
        TextView name = new TextView(context);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        name.setText(ClickerGraphics.getIngredientName(level, context));
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTypeface(null, Typeface.BOLD);
        namePrizeLayout.addView(name);
        // crepes per seconds (one)
        TextView crepesPerSecondsOne = new TextView(context);
        crepesPerSecondsOne.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        crepesPerSecondsOne.setText(context.getResources().getString(R.string.clicker_produce_one) + " "
                + getCrepesPerSecondsOne(clicker, level, context));
        crepesPerSecondsOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        namePrizeLayout.addView(crepesPerSecondsOne);
        // crepes per seconds (all)
        TextView crepesPerSecondsAll = new TextView(context);
        crepesPerSecondsAll.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        crepesPerSecondsAll.setText(context.getResources().getString(R.string.clicker_produce_all) + " "
                + getCrepesPerSecondsAll(clicker, level, context));
        crepesPerSecondsAll.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        namePrizeLayout.addView(crepesPerSecondsAll);
        // add name and prize
        root.addView(namePrizeLayout);

        return root;
    }

    public static String getCrepesPerSecondsOne(Clicker clicker, int level, Context context){
        // butter = n * (money/s + SOM(k=4 -> upgrades)(butterAddition_k * nOthers)) * 2^(upgrades > 3 ? 3 : upgrades)
        if(level == 0){
            double butterAddition = 0;
            double upgrades;
            if(clicker.getUpgradesFlavorsAt(0) > 3){
                upgrades = 3;
                for(int i=4; i<clicker.getUpgradesFlavorsAt(0); i++)
                    for(int j=1; j<clicker.level; j++)
                        butterAddition += (double) clicker.getUpgradesFlavorsAt(j) * ClickerStatics.butterAddition[i];
            }else
                upgrades = clicker.getUpgradesFlavorsAt(0);
            return ClickerUtil.humanReadableNumbersCounterPS((ClickerStatics.ingredientsBaseMoneyPerSecond[0] + butterAddition) * Math.pow(2, upgrades), context);
        }

        // others = n * money/s * 2^upgrades
        return ClickerUtil.humanReadableNumbersCounterPS(ClickerStatics.ingredientsBaseMoneyPerSecond[level] * Math.pow(2, clicker.getUpgradesFlavorsAt(level)), context);
    }

    public static String getCrepesPerSecondsAll(Clicker clicker, int level, Context context){
        // butter = n * (money/s + SOM(k=4 -> upgrades)(butterAddition_k * nOthers)) * 2^(upgrades > 3 ? 3 : upgrades)
        if(level == 0){
            double butterAddition = 0;
            double upgrades;
            if(clicker.getUpgradesFlavorsAt(0) > 3){
                upgrades = 3;
                for(int i=4; i<clicker.getUpgradesFlavorsAt(0); i++)
                    for(int j=1; j<clicker.level; j++)
                        butterAddition += (double) clicker.getUpgradesFlavorsAt(j) * ClickerStatics.butterAddition[i];
            }else
                upgrades = clicker.getUpgradesFlavorsAt(0);
            return ClickerUtil.humanReadableNumbersCounterPS((double) clicker.getFlavorAt(level) * (ClickerStatics.ingredientsBaseMoneyPerSecond[0] + butterAddition)
                    * Math.pow(2, upgrades), context);
        }

        // others = n * money/s * 2^upgrades
        return ClickerUtil.humanReadableNumbersCounterPS((double) clicker.getFlavorAt(level) * ClickerStatics.ingredientsBaseMoneyPerSecond[level]
                * Math.pow(2, clicker.getUpgradesFlavorsAt(level)), context);
    }
}
