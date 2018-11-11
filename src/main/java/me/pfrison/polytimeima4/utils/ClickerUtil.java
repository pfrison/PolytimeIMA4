package me.pfrison.polytimeima4.utils;

import android.content.Context;

import me.pfrison.polytimeima4.R;

public class ClickerUtil {

    public static String encryptSave(String save){
        byte[] bytes = save.getBytes();
        StringBuilder encrypt = new StringBuilder();
        for(byte b : bytes)
            encrypt.append(String.format("%02x", b).toUpperCase());
        return encrypt.toString();
    }
    public static String decryptSave(String save){
        byte[] bytes = new byte[save.length() / 2];
        for (int i=0; i < save.length()/2; i++)
            bytes[i] = (byte) ((Character.digit(save.charAt(i*2), 16) << 4)
                    + Character.digit(save.charAt((i*2)+1), 16));
        return new String(bytes);
    }

    public static String removeNewLineChar(String str){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) != '\n')
                stringBuilder.append(str.charAt(i));
        }
        return stringBuilder.toString();
    }

    public static String humanReadableNumbersCounter(double number, Context context) {
        if (number < 1000000){ // below million
            if (number < 2) // singular
                return String.valueOf((int) number) + " " + context.getResources().getString(R.string.clicker_money);
            // plural
            // if number > 1000 add a space after 3 zeros
            if(number / 1000d > 1){
                String numberStr = String.valueOf((int) number);
                return numberStr.substring(0, numberStr.length() - 3) + " "
                        + numberStr.substring(numberStr.length() - 3, numberStr.length()) + " "
                        + context.getResources().getString(R.string.clicker_moneys);
            }
            return String.valueOf((int) number) + " " + context.getResources().getString(R.string.clicker_moneys);
        }
        return humanReadableNumbersOverMillion(number, R.string.clicker_moneys, context);
    }
    public static String humanReadableNumbersCounterPS(double number, Context context) {
        if (number < 1000000){ // below million
            if(number % 1 == 0){ // whole number
                if(number < 2) // singular
                    return String.valueOf((int) number) + " " + context.getResources().getString(R.string.clicker_money_per_sec);
                // plural
                // if number > 1000 add a space after 3 zeros
                if(number / 1000d > 1){
                    String numberStr = String.valueOf((int) number);
                    return numberStr.substring(0, numberStr.length() - 3) + " "
                            + numberStr.substring(numberStr.length() - 3, numberStr.length()) + " "
                            + context.getResources().getString(R.string.clicker_moneys_per_sec);
                }
                return String.valueOf((int) number) + " " + context.getResources().getString(R.string.clicker_moneys_per_sec);
            }else{ // decimal
                if(number < 2) // singular
                    return String.format("%.1f", number) + " " + context.getResources().getString(R.string.clicker_money_per_sec);
                // plural
                // if number > 1000 add a space after 3 zeros
                if(number / 1000d > 1){
                    String numberStr = String.format("%.1f", number);
                    return numberStr.substring(0, numberStr.length() - 3) + " "
                            + numberStr.substring(numberStr.length() - 3, numberStr.length()) + " "
                            + context.getResources().getString(R.string.clicker_moneys_per_sec);
                }
                return String.format("%.1f", number) + " " + context.getResources().getString(R.string.clicker_moneys_per_sec);
            }
        }
        return humanReadableNumbersOverMillion(number, R.string.clicker_moneys_per_sec, context);
    }
    private static String humanReadableNumbersOverMillion(double number, int endingStrResId, Context context){
        for(int i=2; i<=16; i++){
            if(number < Math.pow(1000, i+1)){
                int[] numberResIds = getNumberResId(i);
                if (number / Math.pow(1000, i) < 2) // singular
                    return formatNumber(number, Math.pow(1000, i)) + " "
                            + context.getResources().getString(numberResIds[0]) + " "
                            + context.getResources().getString(endingStrResId);
                // plural
                return formatNumber(number, Math.pow(1000, i)) + " "
                        + context.getResources().getString(numberResIds[1]) + " "
                        + context.getResources().getString(endingStrResId);
            }
        }
        return context.getResources().getString(R.string.infinity);
    }
    private static String formatNumber(double number, double divider){return String.format("%.3f", number / divider);}
    private static int[] getNumberResId(int i){
        switch (i){
            case 2:
                return new int[]{
                        R.string.million,
                        R.string.millions
                };
            case 3:
                return new int[]{
                        R.string.billion,
                        R.string.billions
                };
            case 4:
                return new int[]{
                        R.string.trillion,
                        R.string.trillions
                };
            case 5:
                return new int[]{
                        R.string.quadrillion,
                        R.string.quadrillions
                };
            case 6:
                return new int[]{
                        R.string.quintillion,
                        R.string.quintillions
                };
            case 7:
                return new int[]{
                        R.string.sextillion,
                        R.string.sextillions
                };
            case 8:
                return new int[]{
                        R.string.septillion,
                        R.string.septillions
                };
            case 9:
                return new int[]{
                        R.string.octillion,
                        R.string.octillions
                };
            case 10:
                return new int[]{
                        R.string.nonillion,
                        R.string.nonillions
                };
            case 11:
                return new int[]{
                        R.string.decillion,
                        R.string.decillions
                };
            case 12:
                return new int[]{
                        R.string.undecillion,
                        R.string.undecillions
                };
            case 13:
                return new int[]{
                        R.string.duodecillion,
                        R.string.duodecillions
                };
            case 14:
                return new int[]{
                        R.string.tredecillion,
                        R.string.tredecillions
                };
            case 15:
                return new int[]{
                        R.string.quattuordecillion,
                        R.string.quattuordecillions
                };
            case 16:
                return new int[]{
                        R.string.quindecillion,
                        R.string.quindecillions
                };
        }
        throw new IllegalArgumentException("invalid number");
    }
}
