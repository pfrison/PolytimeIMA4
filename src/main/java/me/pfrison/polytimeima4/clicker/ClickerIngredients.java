package me.pfrison.polytimeima4.clicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import me.pfrison.polytimeima4.achievements.Achievement;
import me.pfrison.polytimeima4.achievements.AchievementPopup;
import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.ClickerGraphics;
import me.pfrison.polytimeima4.utils.ClickerUtil;

public class ClickerIngredients {
    private Context context;
    private Clicker clicker;

    private ImageView clickerButton;

    private LinearLayout ingredientRootLayout;
    private ArrayList<LinearLayout> ingredientLayouts = new ArrayList<>();
    private ArrayList<Boolean> canBuyIngredients = new ArrayList<>();

    public ClickerIngredients(Context context, Clicker clicker, LinearLayout ingredientRootLayout, ImageView clickerButton, AchievementPopup achievementPopup){
        this.context = context;
        this.clicker = clicker;
        this.clickerButton = clickerButton;

        // build ingredients up to level and secret ingredient
        this.ingredientRootLayout = ingredientRootLayout;
        ingredientLayouts = new ArrayList<>();
        canBuyIngredients = new ArrayList<>();
        for(int i=0; i<clicker.level + 1; i++){
            LinearLayout layout;
            if(i < clicker.level)
                layout = createIngredient(i, clicker.getFlavorAt(i));
            else
                layout = createSecretIngredient(i);
            if(layout == null)
                continue;
            ingredientLayouts.add(layout);
            ingredientRootLayout.addView(layout);

            // can buy
            boolean canBuy = ClickerMechanics.canBuyIngredient(i, clicker);
            canBuyIngredients.add(canBuy);
            if(!canBuy)
                ClickerGraphics.darkenLayout(ingredientLayouts.get(i), true);

            // achievements for freezer and space ship ingredients
            if(i == 5) // freezer
                Achievement.achievements[Achievement.ID_CLICKER_FREEZER].setDone(achievementPopup);
            if(i == 13) // space ship
                Achievement.achievements[Achievement.ID_CLICKER_EARTH].setDone(achievementPopup);

            // click listener
            layout.setOnClickListener(getIngredientOnClickListener(i, achievementPopup));
        }
    }

    private LinearLayout createIngredient(int level, int count){
        LinearLayout root = new LinearLayout(context);
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.setOrientation(LinearLayout.HORIZONTAL);

        // icon
        ImageView icon = new ImageView(context);
        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.clicker_item_icon_size);
        icon.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize, 0));
        icon.setImageResource(ClickerGraphics.getIngredientsResId(level));
        root.addView(icon);

        //name, description and prize
        LinearLayout namePrizeLayout = new LinearLayout(context);
        namePrizeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        namePrizeLayout.setPadding(context.getResources().getDimensionPixelSize(R.dimen.clicker_item_padding), 0, 0, 0);
        namePrizeLayout.setOrientation(LinearLayout.VERTICAL);
        // name
        TextView name = new TextView(context);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        name.setText(ClickerGraphics.getIngredientName(level, context));
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTypeface(null, Typeface.BOLD);
        namePrizeLayout.addView(name);
        // description
        TextView description = new TextView(context);
        description.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        description.setText(ClickerGraphics.getIngredientDescription(level, context));
        description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        namePrizeLayout.addView(description);
        //prize
        TextView prize = new TextView(context);
        prize.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        prize.setText(context.getResources().getString(R.string.clicker_cost) + " "
                + ClickerUtil.humanReadableNumbersCounter(ClickerMechanics.getIngredientPrize(level, count), context));
        prize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        namePrizeLayout.addView(prize);
        // add name and prize
        root.addView(namePrizeLayout);

        // count
        TextView countTV = new TextView(context);
        countTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
        countTV.setText(String.valueOf(count));
        countTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        countTV.setTypeface(null, Typeface.BOLD);
        root.addView(countTV);

        return root;
    }

    private LinearLayout createSecretIngredient(int level){
        if(level >= ClickerStatics.INGREDIENTS_COUNT)
            return null;

        LinearLayout root = new LinearLayout(context);
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.setOrientation(LinearLayout.HORIZONTAL);

        // icon
        ColorFilter filter = new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        Paint paint = new Paint();
        paint.setColorFilter(filter);

        ImageView icon = new ImageView(context);
        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.clicker_item_icon_size);
        icon.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize, 0));
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), ClickerGraphics.getIngredientsResId(level))
                .copy(Bitmap.Config.ARGB_8888, true);
        new Canvas(bitmap).drawBitmap(bitmap, 0, 0, paint);
        icon.setImageBitmap(bitmap);
        root.addView(icon);

        //name and prize
        LinearLayout namePrizeLayout = new LinearLayout(context);
        namePrizeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        namePrizeLayout.setPadding(context.getResources().getDimensionPixelSize(R.dimen.clicker_item_padding), 0, 0, 0);
        namePrizeLayout.setOrientation(LinearLayout.VERTICAL);
        // name
        TextView name = new TextView(context);
        name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        name.setText("???");
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        name.setTypeface(null, Typeface.BOLD);
        namePrizeLayout.addView(name);
        // description
        TextView description = new TextView(context);
        description.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        description.setText("??????");
        description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        namePrizeLayout.addView(description);
        //prize
        TextView prize = new TextView(context);
        prize.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        prize.setText(context.getResources().getString(R.string.clicker_cost) + " "
                + ClickerUtil.humanReadableNumbersCounter(ClickerMechanics.getIngredientPrize(level, 0), context));
        prize.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        namePrizeLayout.addView(prize);
        // add name and prize
        root.addView(namePrizeLayout);

        // count
        TextView countTV = new TextView(context);
        countTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
        countTV.setText("0");
        countTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        countTV.setTypeface(null, Typeface.BOLD);
        root.addView(countTV);

        return root;
    }

    private View.OnClickListener getIngredientOnClickListener(final int pos, final AchievementPopup achievementPopup){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canBuyIngredients.get(pos))
                    return;

                // subtract money
                ClickerMechanics.buyIngredient(clicker, pos);
                recheckAllCanBuy();

                // update layouts
                if (clicker.getFlavorAt(pos) >= 2) {
                    // update count
                    TextView count = (TextView) ingredientLayouts.get(pos).getChildAt(2);
                    count.setText(String.valueOf(clicker.getFlavorAt(pos)));

                    //update prize
                    TextView prize = (TextView) ((ViewGroup) ingredientLayouts.get(pos).getChildAt(1)).getChildAt(2);
                    prize.setText(context.getResources().getString(R.string.clicker_cost) + " "
                            + ClickerUtil.humanReadableNumbersCounter(ClickerMechanics.getIngredientPrize(pos, clicker.getFlavorAt(pos)), context));

                    // can buy again
                    boolean canBuy = ClickerMechanics.canBuyIngredient(pos, clicker);
                    canBuyIngredients.set(pos, canBuy);
                    if (!canBuy)
                        ClickerGraphics.darkenLayout(ingredientLayouts.get(pos), true);
                }else {
                    addNextFlavor(achievementPopup);
                    // achievements for freezer and space ship ingredients
                    if(pos == 5) // freezer
                        Achievement.achievements[Achievement.ID_CLICKER_FREEZER].setDone(achievementPopup);
                    if(pos == 13) // space ship
                        Achievement.achievements[Achievement.ID_CLICKER_EARTH].setDone(achievementPopup);
                }
            }
        };
    }

    public void recheckAllCanBuy(){
        for(int i=0; i<canBuyIngredients.size(); i++){
            boolean canBuy = ClickerMechanics.canBuyIngredient(i, clicker);
            if (!canBuy && canBuyIngredients.get(i)) // can buy -> can't buy
                ClickerGraphics.darkenLayout(ingredientLayouts.get(i), true);
            if (canBuy && !canBuyIngredients.get(i)) // can't buy -> can buy
                ClickerGraphics.lightenLayout(ingredientLayouts.get(i), true);
            canBuyIngredients.set(i, canBuy);
        }
    }

    private void addNextFlavor(AchievementPopup achievementPopup){
        // change the crepe design
        clickerButton.setImageResource(ClickerGraphics.getFlavorsResId(clicker.level));

        // remove layout
        ingredientRootLayout.removeViewAt(clicker.level - 1);
        ingredientLayouts.remove(clicker.level - 1);

        // add the ingredient + the secret ingredient
        for(int i=clicker.level - 1; i<clicker.level + 1; i++){
            LinearLayout layout;
            if(i < clicker.level)
                layout = createIngredient(i, clicker.getFlavorAt(i));
            else
                layout = createSecretIngredient(i);
            if(layout == null)
                continue;
            ingredientLayouts.add(layout);
            ingredientRootLayout.addView(layout);

            // can buy
            boolean canBuy = ClickerMechanics.canBuyIngredient(i, clicker);
            if(i < clicker.level)
                canBuyIngredients.set(i, canBuy);
            else
                canBuyIngredients.add(canBuy);
            if(!canBuy)
                ClickerGraphics.darkenLayout(ingredientLayouts.get(i), true);

            // click listener
            layout.setOnClickListener(getIngredientOnClickListener(i, achievementPopup));
        }
    }
}
