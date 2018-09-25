package me.pfrison.polytimeima4.graphics.style;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.android.MainActivity;
import me.pfrison.polytimeima4.android.SettingsActivity;

public class Style {
    // ---- COLORS
    static final int RED = 0;
    public static final int MAGENTA = 1;
    static final int BLUE = 2;
    public static final int CYAN = 3;
    static final int GREEN = 4;
    public static final int YELLOW = 5;
    static final int ORANGE = 6;

    private static final int RED_NAME = R.string.red_name;
    private static final int MAGENTA_NAME = R.string.magenta_name;
    private static final int BLUE_NAME = R.string.blue_name;
    private static final int CYAN_NAME = R.string.cyan_name;
    private static final int GREEN_NAME = R.string.green_name;
    private static final int YELLOW_NAME = R.string.yellow_name;
    private static final int ORANGE_NAME = R.string.orange_name;

    // ---- VERSIONS
    public static final int DARK = 0;
    static final int LIGHT = 1;

    private static final int DARK_NAME = R.string.dark_name;
    private static final int LIGHT_NAME = R.string.light_name;

    // ---- Lists
    public static final StyleColor[] STYLE_COLOR_LIST = new StyleColor[]{
            new StyleColor(RED, RED_NAME),
            new StyleColor(MAGENTA, MAGENTA_NAME),
            new StyleColor(BLUE, BLUE_NAME),
            new StyleColor(CYAN, CYAN_NAME),
            new StyleColor(GREEN, GREEN_NAME),
            new StyleColor(YELLOW, YELLOW_NAME),
            new StyleColor(ORANGE, ORANGE_NAME)
    };
    public static final StyleVersion[] STYLE_VERSION_LIST = new StyleVersion[]{
            new StyleVersion(DARK, DARK_NAME),
            new StyleVersion(LIGHT, LIGHT_NAME)
    };

    // ---- SharedPreferences keys
    public static final String colorKey = "styleColor";
    public static final String versionKey = "styleVersion";

    private static int getStyleResId(int color, int version) {
        switch (color) {
            case RED:
                switch (version) {
                    case DARK:
                        return R.style.DarkRedTheme;
                    case LIGHT:
                        return R.style.LightRedTheme;
                }
            case MAGENTA:
                switch (version) {
                    case DARK:
                        return R.style.DarkMagentaTheme;
                    case LIGHT:
                        return R.style.LightMagentaTheme;
                }
            case BLUE:
                switch (version) {
                    case DARK:
                        return R.style.DarkBlueTheme;
                    case LIGHT:
                        return R.style.LightBlueTheme;
                }
            case CYAN:
                switch (version) {
                    case DARK:
                        return R.style.DarkCyanTheme;
                    case LIGHT:
                        return R.style.LightCyanTheme;
                }
            case GREEN:
                switch (version) {
                    case DARK:
                        return R.style.DarkGreenTheme;
                    case LIGHT:
                        return R.style.LightGreenTheme;
                }
            case YELLOW:
                switch (version) {
                    case DARK:
                        return R.style.DarkYellowTheme;
                    case LIGHT:
                        return R.style.LightYellowTheme;
                }
            case ORANGE:
                switch (version) {
                    case DARK:
                        return R.style.DarkOrangeTheme;
                    case LIGHT:
                        return R.style.LightOrangeTheme;
                }
        }
        // default
        return R.style.LightRedTheme;
    }

    private static int getColorPrimaryResId(int color){
        switch (color) {
            case RED:
                return R.color.colorRedPrimary;
            case MAGENTA:
                return R.color.colorMagentaPrimary;
            case BLUE:
                return R.color.colorBluePrimary;
            case CYAN:
                return R.color.colorCyanPrimary;
            case GREEN:
                return R.color.colorGreenPrimary;
            case YELLOW:
                return R.color.colorYellowPrimary;
            case ORANGE:
                return R.color.colorOrangePrimary;
            default:
                return R.color.colorRedPrimary;
        }
    }

    private static int getColorPrimaryDarkResId(int color){
        switch (color) {
            case RED:
                return R.color.colorRedPrimaryDark;
            case MAGENTA:
                return R.color.colorMagentaPrimaryDark;
            case BLUE:
                return R.color.colorBluePrimaryDark;
            case CYAN:
                return R.color.colorCyanPrimaryDark;
            case GREEN:
                return R.color.colorGreenPrimaryDark;
            case YELLOW:
                return R.color.colorYellowPrimaryDark;
            case ORANGE:
                return R.color.colorOrangePrimaryDark;
            default:
                return R.color.colorRedPrimaryDark;
        }
    }

    private static int getVersionAchievementBackgroundResId(int version){
        switch (version) {
            case DARK:
                return R.drawable.achievement_popup_dark_background;
            case LIGHT:
                return R.drawable.achievement_popup_light_background;
            default:
                return R.drawable.achievement_popup_dark_background;
        }
    }

    private static int getLessonActiveTransparencyResId(boolean active, int version){
        switch (version) {
            case DARK:
                return active ? R.color.lesson_dark_active_mask : R.color.lesson_dark_dayactive_mask;
            case LIGHT:
                return active ? R.color.lesson_light_active_mask : R.color.lesson_light_dayactive_mask;
            default:
                return active ? R.color.lesson_dark_active_mask : R.color.lesson_dark_dayactive_mask;
        }
    }

    public static int getThemeResIdFromPreferences(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return getStyleResId(pref.getInt(colorKey, 0), pref.getInt(versionKey, 0));
    }

    public static int getColorPrimaryResIdFromPreferences(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return getColorPrimaryResId(pref.getInt(colorKey, 0));
    }

    public static int getColorPrimaryDarkResIdFromPreferences(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return getColorPrimaryDarkResId(pref.getInt(colorKey, 0));
    }

    public static void styliseAchievementPopup(View achievementRoot, Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int backgroundResId = getVersionAchievementBackgroundResId(pref.getInt(versionKey, DARK));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            achievementRoot.setBackground(context.getResources().getDrawable(backgroundResId, null));
        else
            achievementRoot.setBackground(context.getResources().getDrawable(backgroundResId));
    }


    // ---- Lesson active / dayactive background
    private static int getLessonActiveBackground(boolean active, Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int color;
        int transparency;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getResources().getColor(getColorPrimaryResId(pref.getInt(colorKey, 0)), null);
            transparency = context.getResources().getColor(getLessonActiveTransparencyResId(active, pref.getInt(versionKey, 0)), null);
        }else {
            color = context.getResources().getColor(getColorPrimaryResId(pref.getInt(colorKey, 0)));
            transparency = context.getResources().getColor(getLessonActiveTransparencyResId(active, pref.getInt(versionKey, 0)));
        }
        return color & transparency;
    }

    public static Drawable createLessonActiveBackground(boolean active, Context context, boolean firstOfRow){
        // generate background color
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setColor(Style.getLessonActiveBackground(active, context));

        // get borders
        Drawable border = context.getResources().getDrawable(R.drawable.lesson_border);

        // mix them up
        Drawable[] layers = new Drawable[]{background, border};
        LayerDrawable splash = new LayerDrawable(layers);

        // correct background position
        int borderWidth = context.getResources().getDimensionPixelSize(R.dimen.lesson_border_width);
        // if the lesson is the first of the row, the background is drawn above the top line
        // we remove 1 border width at the top to correct the problem
        if(firstOfRow)
            splash.setLayerInset(0, borderWidth, borderWidth, 0, borderWidth);
        else
            splash.setLayerInset(0, borderWidth, 0, 0, borderWidth);
        return splash;
    }
}
