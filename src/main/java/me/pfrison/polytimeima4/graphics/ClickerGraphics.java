package me.pfrison.polytimeima4.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.clicker.ClickerStatics;
import me.pfrison.polytimeima4.clicker.golden.Golden;
import me.pfrison.polytimeima4.clicker.upgrade.Upgrade;
import me.pfrison.polytimeima4.utils.ClickerUtil;

public class ClickerGraphics {
    private static int[] FLAVORS = new int[]{
            R.mipmap.crepe,
            R.mipmap.crepe_beurre,
            R.mipmap.crepe_sucre,
            R.mipmap.crepe_caramel,
            R.mipmap.crepe_confiture,
            R.mipmap.crepe_chocolat,
            R.mipmap.crepe_congele,
            R.mipmap.crepe_2n2222,
            R.mipmap.crepe_lm748,
            R.mipmap.crepe_74hc4040,
            R.mipmap.crepe_atmega328,
            R.mipmap.crepe_kernel,
            R.mipmap.crepe_reseau,
            R.mipmap.crepe_robotique,
            R.mipmap.crepe_terre
    };
    private static int[] INGREDIENTS = new int[]{
            R.mipmap.ingredient_beurre,
            R.mipmap.ingredient_sucre,
            R.mipmap.ingredient_caramel,
            R.mipmap.ingredient_confiture,
            R.mipmap.ingredient_chocolat,
            R.mipmap.ingredient_freezer,
            R.mipmap.ingredient_2n2222,
            R.mipmap.ingredient_lm748,
            R.mipmap.ingredient_74hc4040,
            R.mipmap.ingredient_atmega328,
            R.mipmap.ingredient_kernel,
            R.mipmap.ingredient_reseau,
            R.mipmap.ingredient_robotique,
            R.mipmap.ingredient_terre
    };
    private static int[] UPGRADE_INGREDIENT_POWERS = new int[]{
            R.color.upgrade_power_0,
            R.color.upgrade_power_1,
            R.color.upgrade_power_2,
            R.color.upgrade_power_3,
            R.color.upgrade_power_4,
            R.color.upgrade_power_5,
            R.color.upgrade_power_6,
            R.color.upgrade_power_7,
            R.color.upgrade_power_8,
            R.color.upgrade_power_9,
            R.color.upgrade_power_10
    };
    private static int[] UPGRADE_INGREDIENT_BITMAPS = new int[]{
            R.mipmap.upgrade_ingredient_beurre,
            R.mipmap.upgrade_ingredient_sucre,
            R.mipmap.upgrade_ingredient_caramel,
            R.mipmap.upgrade_ingredient_confiture,
            R.mipmap.upgrade_ingredient_chocolat,
            R.mipmap.upgrade_ingredient_freezer,
            R.mipmap.upgrade_ingredient_2n2222,
            R.mipmap.upgrade_ingredient_lm748,
            R.mipmap.upgrade_ingredient_74hc4040,
            R.mipmap.upgrade_ingredient_atmega328,
            R.mipmap.upgrade_ingredient_kernel,
            R.mipmap.upgrade_ingredient_reseau,
            R.mipmap.upgrade_ingredient_robotique,
            R.mipmap.upgrade_ingredient_terre,
    };
    private static int[] UPGRADE_INGREDIENT_NAME_ARRAYS = new int[]{
            R.array.clicker_upgrade_ingredient_butter,
            R.array.clicker_upgrade_ingredient_sugar,
            R.array.clicker_upgrade_ingredient_caramel,
            R.array.clicker_upgrade_ingredient_jam,
            R.array.clicker_upgrade_ingredient_chocolate,
            R.array.clicker_upgrade_ingredient_freezer,
            R.array.clicker_upgrade_ingredient_2n2222,
            R.array.clicker_upgrade_ingredient_lm748,
            R.array.clicker_upgrade_ingredient_74hc4040,
            R.array.clicker_upgrade_ingredient_atmega328,
            R.array.clicker_upgrade_ingredient_kernel,
            R.array.clicker_upgrade_ingredient_networks,
            R.array.clicker_upgrade_ingredient_robotics,
            R.array.clicker_upgrade_ingredient_earth
    };
    private static int[] UPGRADE_INGREDIENT_DESCRIPTION_ARRAYS = new int[]{
            R.array.clicker_upgrade_ingredient_butter_description,
            R.array.clicker_upgrade_ingredient_sugar_description,
            R.array.clicker_upgrade_ingredient_caramel_description,
            R.array.clicker_upgrade_ingredient_jam_description,
            R.array.clicker_upgrade_ingredient_chocolate_description,
            R.array.clicker_upgrade_ingredient_freezer_description,
            R.array.clicker_upgrade_ingredient_2n2222_description,
            R.array.clicker_upgrade_ingredient_lm748_description,
            R.array.clicker_upgrade_ingredient_74hc4040_description,
            R.array.clicker_upgrade_ingredient_atmega328_description,
            R.array.clicker_upgrade_ingredient_kernel_description,
            R.array.clicker_upgrade_ingredient_networks_description,
            R.array.clicker_upgrade_ingredient_robotics_description,
            R.array.clicker_upgrade_ingredient_earth_description
    };
    private static int[] UPGRADE_SUPPLEMENTS_BITMAPS = new int[]{
            R.mipmap.supplement_banane,
            R.mipmap.supplement_cassis,
            R.mipmap.supplement_mure,
            R.mipmap.supplement_oignon,
            R.mipmap.supplement_peche,
            R.mipmap.supplement_fraise,
            R.mipmap.supplement_framboise,
            R.mipmap.supplement_groseille,
            R.mipmap.supplement_melon,
            R.mipmap.supplement_tomate,
            R.mipmap.supplement_salade,
            R.mipmap.supplement_ail,
            R.mipmap.supplement_noisette,
            R.mipmap.supplement_cerise,
            R.mipmap.supplement_chou,
            R.mipmap.supplement_rhubarbe,
            R.mipmap.supplement_aubergine,
            R.mipmap.supplement_patate,
            R.mipmap.supplement_raisin,
            R.mipmap.supplement_courgette,
            R.mipmap.supplement_potiron,
            R.mipmap.supplement_orange,
            R.mipmap.supplement_prune,
            R.mipmap.supplement_noix_de_coco,
            R.mipmap.supplement_clementine,
            R.mipmap.supplement_abricot,
            R.mipmap.supplement_carotte,
            R.mipmap.supplement_poireau,
            R.mipmap.supplement_poire,
            R.mipmap.supplement_pomme,
            R.mipmap.supplement_ananas,
            R.mipmap.supplement_citron,
            R.mipmap.supplement_champignon
    };
    private static int[] UPGRADE_GLOVES_BITMAPS = new int[]{
            R.mipmap.gant_cuir,
            R.mipmap.gant_cuivre,
            R.mipmap.gant_fer,
            R.mipmap.gant_gallium,
            R.mipmap.gant_acier,
            R.mipmap.gant_argent,
            R.mipmap.gant_or,
            R.mipmap.gant_titane,
            R.mipmap.gant_tungstene,
            R.mipmap.gant_obsidienne,
            R.mipmap.gant_diamant,
            R.mipmap.gant_adamantium
    };

    public static int getFlavorsResId(int level){
        if(level >= FLAVORS.length)
            return FLAVORS[FLAVORS.length - 1];
        if(level < 0)
            return FLAVORS[0];
        return FLAVORS[level];
    }

    public static int getIngredientsResId(int level){
        if(level >= INGREDIENTS.length)
            return INGREDIENTS[INGREDIENTS.length - 1];
        if(level < 0)
            return INGREDIENTS[0];
        return INGREDIENTS[level];
    }

    public static String getIngredientName(int level, Context context){
        String[] names = context.getResources().getStringArray(R.array.clicker_ingredients_name);
        if(level >= names.length)
            return names[names.length - 1];
        if(level < 0)
            return names[0];
        return names[level];
    }

    public static String getIngredientDescription(int level, Context context){
        String[] descriptions = context.getResources().getStringArray(R.array.clicker_ingredients_description);
        if(level >= descriptions.length)
            return descriptions[descriptions.length - 1];
        if(level < 0)
            return descriptions[0];
        return descriptions[level];
    }

    public static Bitmap getUpgradeIcon(Upgrade upgrade, Context context){
        switch (upgrade.type){
            case Upgrade.INGREDIENT:
                // ingredient colorless icon
                int bitmapResId;
                if(upgrade.identifier[0] >= UPGRADE_INGREDIENT_BITMAPS.length)
                    bitmapResId = UPGRADE_INGREDIENT_BITMAPS[UPGRADE_INGREDIENT_BITMAPS.length - 1];
                else if(upgrade.identifier[0] < 0)
                    bitmapResId = UPGRADE_INGREDIENT_BITMAPS[0];
                else
                    bitmapResId = UPGRADE_INGREDIENT_BITMAPS[upgrade.identifier[0]];

                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapResId)
                        .copy(Bitmap.Config.ARGB_8888, true);

                // add color according to upgrade power level
                int colorResId;
                if(upgrade.identifier[1] >= UPGRADE_INGREDIENT_POWERS.length)
                    colorResId = UPGRADE_INGREDIENT_POWERS[UPGRADE_INGREDIENT_POWERS.length - 1];
                else if(upgrade.identifier[1] < 0)
                    colorResId = UPGRADE_INGREDIENT_POWERS[0];
                else
                    colorResId = UPGRADE_INGREDIENT_POWERS[upgrade.identifier[1]];

                int color;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    color = context.getResources().getColor(colorResId, null);
                else
                    color = context.getResources().getColor(colorResId);

                ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
                Paint paint = new Paint();
                paint.setColorFilter(filter);
                new Canvas(bitmap).drawBitmap(bitmap, 0, 0, paint);
                return bitmap;
            case Upgrade.SUPPLEMENT:
                if(upgrade.identifier[0] >= UPGRADE_SUPPLEMENTS_BITMAPS.length)
                    return BitmapFactory.decodeResource(context.getResources(), UPGRADE_SUPPLEMENTS_BITMAPS[UPGRADE_SUPPLEMENTS_BITMAPS.length - 1])
                            .copy(Bitmap.Config.ARGB_8888, true);
                if(upgrade.identifier[0] < 0)
                    return BitmapFactory.decodeResource(context.getResources(), UPGRADE_SUPPLEMENTS_BITMAPS[0])
                            .copy(Bitmap.Config.ARGB_8888, true);
                return BitmapFactory.decodeResource(context.getResources(), UPGRADE_SUPPLEMENTS_BITMAPS[upgrade.identifier[0]])
                        .copy(Bitmap.Config.ARGB_8888, true);
            case Upgrade.GLOVE:
                if(upgrade.identifier[0] >= UPGRADE_GLOVES_BITMAPS.length)
                    return BitmapFactory.decodeResource(context.getResources(), UPGRADE_GLOVES_BITMAPS[UPGRADE_GLOVES_BITMAPS.length - 1])
                            .copy(Bitmap.Config.ARGB_8888, true);
                if(upgrade.identifier[0] < 0)
                    return BitmapFactory.decodeResource(context.getResources(), UPGRADE_GLOVES_BITMAPS[0])
                            .copy(Bitmap.Config.ARGB_8888, true);
                return BitmapFactory.decodeResource(context.getResources(), UPGRADE_GLOVES_BITMAPS[upgrade.identifier[0]])
                        .copy(Bitmap.Config.ARGB_8888, true);
            case Upgrade.GOLDEN:
                return BitmapFactory.decodeResource(context.getResources(), R.mipmap.golden_upgrade)
                        .copy(Bitmap.Config.ARGB_8888, true);
        }
        return BitmapFactory.decodeResource(context.getResources(), R.mipmap.crepe)
                .copy(Bitmap.Config.ARGB_8888, true);
    }

    public static String getUpgradeName(Upgrade upgrade, Context context){
        String[] names;
        switch (upgrade.type){
            case Upgrade.INGREDIENT:
                // get array name
                int nameArrayResId;
                if(upgrade.identifier[0] >= UPGRADE_INGREDIENT_NAME_ARRAYS.length)
                    nameArrayResId = UPGRADE_INGREDIENT_NAME_ARRAYS[UPGRADE_INGREDIENT_NAME_ARRAYS.length - 1];
                else if(upgrade.identifier[0] < 0)
                    nameArrayResId = UPGRADE_INGREDIENT_NAME_ARRAYS[0];
                else
                    nameArrayResId = UPGRADE_INGREDIENT_NAME_ARRAYS[upgrade.identifier[0]];

                // get name
                names = context.getResources().getStringArray(nameArrayResId);
                if(upgrade.identifier[1] >= names.length)
                    return names[names.length - 1];
                if(upgrade.identifier[1] < 0)
                    return names[0];
                return names[upgrade.identifier[1]];
            case Upgrade.SUPPLEMENT:
                names = context.getResources().getStringArray(R.array.clicker_upgrade_supplements);
                if(upgrade.identifier[0] >= names.length)
                    return names[names.length - 1];
                if(upgrade.identifier[0] < 0)
                    return names[0];
                return names[upgrade.identifier[0]];
            case Upgrade.GLOVE:
                names = context.getResources().getStringArray(R.array.clicker_upgrade_gloves_name);
                if(upgrade.identifier[0] >= names.length)
                    return names[names.length - 1];
                if(upgrade.identifier[0] < 0)
                    return names[0];
                return names[upgrade.identifier[0]];
            case Upgrade.GOLDEN:
                names = context.getResources().getStringArray(R.array.clicker_upgrade_golden_name);
                if(upgrade.identifier[0] >= names.length)
                    return names[names.length - 1];
                if(upgrade.identifier[0] < 0)
                    return names[0];
                return names[upgrade.identifier[0]];
        }
        return "Err";
    }

    public static String getUpgradeDescription(Upgrade upgrade, Context context){
        String[] desccriptions;
        String effect;
        switch (upgrade.type){
            case Upgrade.INGREDIENT:
                // get array description
                int descriptionArrayResId;
                if(upgrade.identifier[0] >= UPGRADE_INGREDIENT_DESCRIPTION_ARRAYS.length)
                    descriptionArrayResId = UPGRADE_INGREDIENT_DESCRIPTION_ARRAYS[UPGRADE_INGREDIENT_DESCRIPTION_ARRAYS.length - 1];
                else if(upgrade.identifier[0] < 0)
                    descriptionArrayResId = UPGRADE_INGREDIENT_DESCRIPTION_ARRAYS[0];
                else
                    descriptionArrayResId = UPGRADE_INGREDIENT_DESCRIPTION_ARRAYS[upgrade.identifier[0]];

                // get description
                desccriptions = context.getResources().getStringArray(descriptionArrayResId);
                if(upgrade.identifier[1] >= desccriptions.length)
                    return desccriptions[desccriptions.length - 1];
                if(upgrade.identifier[1] < 0)
                    return desccriptions[0];

                // get effect description
                if(upgrade.identifier[0] == 0 && upgrade.identifier[1] >= 3) {
                    if(ClickerStatics.butterAddition[upgrade.identifier[1]] % 1 == 0) // is whole
                        effect = String.format(context.getResources().getString(R.string.clicker_upgrade_butter_whole),
                                (int) ClickerStatics.butterAddition[upgrade.identifier[1]]);
                    else
                        effect = String.format(context.getResources().getString(R.string.clicker_upgrade_butter_decimal),
                                ClickerStatics.butterAddition[upgrade.identifier[1]]);
                }else {
                    String preposisition;
                    switch (upgrade.identifier[0]){
                        case 0:case 1:case 2:case 4:
                            preposisition = "du";
                            break;
                        case 3:
                            preposisition = "de la";
                            break;
                        default:
                            preposisition = "des";
                            break;
                    }
                    effect = String.format(context.getResources().getString(R.string.clicker_double),
                            preposisition + " " + context.getResources().getStringArray(R.array.clicker_ingredients_name_plural)[upgrade.identifier[0]]);
                }
                return effect + "\n(" + desccriptions[upgrade.identifier[1]] + ")";
            case Upgrade.SUPPLEMENT:
                desccriptions = context.getResources().getStringArray(R.array.clicker_upgrade_supplements_description);
                if(upgrade.identifier[0] >= desccriptions.length)
                    return desccriptions[desccriptions.length - 1];
                if(upgrade.identifier[0] < 0)
                    return desccriptions[0];
                effect = String.format(context.getResources().getString(R.string.clicker_supplements),
                        (int) (100 * (ClickerStatics.supplementsMultiplications[upgrade.identifier[0]] - 1)));
                return effect + "\n(" + desccriptions[upgrade.identifier[0]] + ")";
            case Upgrade.GLOVE:
                desccriptions = context.getResources().getStringArray(R.array.clicker_upgrade_gloves_description);
                if(upgrade.identifier[0] >= desccriptions.length)
                    return desccriptions[desccriptions.length - 1];
                if(upgrade.identifier[0] < 0)
                    return desccriptions[0];
                effect = context.getResources().getString(R.string.clicker_upgrade_glove);
                return effect + "\n(" + desccriptions[upgrade.identifier[0]] + ")";
            case Upgrade.GOLDEN:
                desccriptions = context.getResources().getStringArray(R.array.clicker_upgrade_golden_description);
                if(upgrade.identifier[0] >= desccriptions.length)
                    return desccriptions[desccriptions.length - 1];
                if(upgrade.identifier[0] < 0)
                    return desccriptions[0];
                // type 1 : short spawn time
                if(upgrade.identifier[0] <= 1) effect = context.getResources().getString(R.string.clicker_upgrade_golden_1);
                // type 2 : double effect duration
                else effect = context.getResources().getString(R.string.clicker_upgrade_golden_2);
                return effect + "\n(" + desccriptions[upgrade.identifier[0]] + ")";
        }
        return "Err";
    }

    public static void darkenLayout(LinearLayout layout, boolean ingredients){
        // icon
        ImageView icon = (ImageView) layout.getChildAt(0);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0.3f);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        icon.setColorFilter(filter);

        // name
        TextView name = (TextView) ((ViewGroup) layout.getChildAt(1)).getChildAt(0);
        name.setTextColor(Color.parseColor("#ff505050"));

        // description
        TextView description = (TextView) ((ViewGroup) layout.getChildAt(1)).getChildAt(1);
        description.setTextColor(Color.parseColor("#ff505050"));

        // prize
        TextView prize = (TextView) ((ViewGroup) layout.getChildAt(1)).getChildAt(2);
        prize.setTextColor(Color.parseColor("#ff505050"));

        // count
        if(ingredients){
            TextView count = (TextView) layout.getChildAt(2);
            count.setTextColor(Color.parseColor("#ff505050"));
        }
    }

    public static void lightenLayout(LinearLayout layout, boolean ingredients){
        // icon
        ImageView icon = (ImageView) layout.getChildAt(0);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(1f);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        icon.setColorFilter(filter);

        // name
        TextView name = (TextView) ((ViewGroup) layout.getChildAt(1)).getChildAt(0);
        name.setTextColor(Color.parseColor("#ffe0e0e0"));

        // description
        TextView description = (TextView) ((ViewGroup) layout.getChildAt(1)).getChildAt(1);
        description.setTextColor(Color.parseColor("#ffe0e0e0"));

        // prize
        TextView prize = (TextView) ((ViewGroup) layout.getChildAt(1)).getChildAt(2);
        prize.setTextColor(Color.parseColor("#ffe0e0e0"));

        // count
        if(ingredients) {
            TextView count = (TextView) layout.getChildAt(2);
            count.setTextColor(Color.parseColor("#ffe0e0e0"));
        }
    }

    public static String getGoldenText(Golden golden, double param, Context context){
        // params is gain or duration
        switch (golden.type){
            case Golden.TYPE_INSTANT_GAIN:
                String gainStr = ClickerUtil.humanReadableNumbersCounter(param, context);
                return String.format(context.getResources().getString(R.string.clicker_golden_instant_gain), gainStr);
            case Golden.TYPE_LIGHT_MULTIPLIER:
                return String.format(context.getResources().getString(R.string.clicker_golden_light_gain), (int) (param / 1000d));
            case Golden.TYPE_HIGH_MULTIPLIER:
                return String.format(context.getResources().getString(R.string.clicker_golden_high_gain), (int) (param / 1000d));
        }
        return "Err";
    }
}
