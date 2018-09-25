package me.pfrison.polytimeima4.graphics.style;

import me.pfrison.polytimeima4.R;

public class StyleColor {
    int colorPrimaryResId;
    int colorPrimaryDarkResId;
    int colorAccentResId;
    int colorNameResId;

    public StyleColor(int color, int colorNameResId){
        this.colorPrimaryResId = getColorPrimaryResId(color);
        this.colorPrimaryDarkResId = getColorPrimaryDarkResId(color);
        this.colorAccentResId = getColorAccentResId(color);
        this.colorNameResId = colorNameResId;
    }

    private static int getColorPrimaryResId(int color){
        switch (color){
            case Style.RED:
                return R.color.colorRedPrimary;
            case Style.MAGENTA:
                return R.color.colorMagentaPrimary;
            case Style.BLUE:
                return R.color.colorBluePrimary;
            case Style.CYAN:
                return R.color.colorCyanPrimary;
            case Style.GREEN:
                return R.color.colorGreenPrimary;
            case Style.YELLOW:
                return R.color.colorYellowPrimary;
            case Style.ORANGE:
                return R.color.colorOrangePrimary;
            default:
                return R.color.colorRedPrimary;
        }
    }

    private static int getColorPrimaryDarkResId(int color){
        switch (color){
            case Style.RED:
                return R.color.colorRedPrimaryDark;
            case Style.MAGENTA:
                return R.color.colorMagentaPrimaryDark;
            case Style.BLUE:
                return R.color.colorBluePrimaryDark;
            case Style.CYAN:
                return R.color.colorCyanPrimaryDark;
            case Style.GREEN:
                return R.color.colorGreenPrimaryDark;
            case Style.YELLOW:
                return R.color.colorYellowPrimaryDark;
            case Style.ORANGE:
                return R.color.colorOrangePrimaryDark;
            default:
                return R.color.colorRedPrimaryDark;
        }
    }

    private static int getColorAccentResId(int color){
        switch (color){
            case Style.RED:
                return R.color.colorRedAccent;
            case Style.MAGENTA:
                return R.color.colorMagentaAccent;
            case Style.BLUE:
                return R.color.colorBlueAccent;
            case Style.CYAN:
                return R.color.colorCyanAccent;
            case Style.GREEN:
                return R.color.colorGreenAccent;
            case Style.YELLOW:
                return R.color.colorYellowAccent;
            case Style.ORANGE:
                return R.color.colorOrangeAccent;
            default:
                return R.color.colorRedAccent;
        }
    }
}
