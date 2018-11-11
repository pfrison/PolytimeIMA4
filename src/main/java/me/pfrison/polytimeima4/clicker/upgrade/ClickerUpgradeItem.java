package me.pfrison.polytimeima4.clicker.upgrade;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.clicker.ClickerMechanics;
import me.pfrison.polytimeima4.clicker.upgrade.Upgrade;
import me.pfrison.polytimeima4.graphics.ClickerGraphics;
import me.pfrison.polytimeima4.utils.ClickerUtil;

public class ClickerUpgradeItem extends LinearLayout {
    public ClickerUpgradeItem(Upgrade upgrade, Context context){
        super(context);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(HORIZONTAL);

        // icon
        ImageView icon = new ImageView(context);
        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.clicker_item_icon_size);
        icon.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize));
        icon.setImageBitmap(ClickerGraphics.getUpgradeIcon(upgrade, context));
        addView(icon);

        //name, description and prize
        LinearLayout namePrizeLayout = new LinearLayout(context);
        namePrizeLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        namePrizeLayout.setPadding(getResources().getDimensionPixelSize(R.dimen.clicker_item_padding), 0, 0, 0);
        namePrizeLayout.setOrientation(LinearLayout.VERTICAL);

        // name
        TextView name = new TextView(context);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        name.setText(ClickerGraphics.getUpgradeName(upgrade, context));
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTypeface(null, Typeface.BOLD);
        namePrizeLayout.addView(name);

        // description
        TextView description = new TextView(context);
        description.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        description.setText(ClickerGraphics.getUpgradeDescription(upgrade, context));
        description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        namePrizeLayout.addView(description);

        // prize
        TextView prize = new TextView(context);
        prize.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        prize.setText(context.getResources().getString(R.string.clicker_cost) + " "
                + ClickerUtil.humanReadableNumbersCounter(ClickerMechanics.getUpgradePrize(upgrade), context));
        prize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        namePrizeLayout.addView(prize);

        // add name and prize
        addView(namePrizeLayout);
    }
}
