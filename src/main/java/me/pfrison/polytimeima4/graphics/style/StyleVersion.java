package me.pfrison.polytimeima4.graphics.style;

import me.pfrison.polytimeima4.R;

public class StyleVersion {
    int versionTextColorResId;
    int versionBackgroundResId;
    int versionNameResId;

    public StyleVersion(int version, int versionNameResId){
        this.versionTextColorResId = getVersionTextColorResId(version);
        this.versionBackgroundResId = getVersionBackgroundResId(version);
        this.versionNameResId = versionNameResId;
    }

    private static int getVersionTextColorResId(int color){
        switch (color){
            case Style.DARK:
                return R.color.dark_text;
            case Style.LIGHT:
                return R.color.light_text;
            default:
                return R.color.dark_text;
        }
    }

    private static int getVersionBackgroundResId(int color){
        switch (color){
            case Style.DARK:
                return R.color.dark_background;
            case Style.LIGHT:
                return R.color.light_background;
            default:
                return R.color.dark_background;
        }
    }
}
