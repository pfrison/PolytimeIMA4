package me.pfrison.polytimeima4.clicker;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.pfrison.polytimeima4.utils.ClickerUtil;
import me.pfrison.polytimeima4.utils.Util;

public class ClickerSaverLoader {
    public static void saveClickerInternal(Clicker clicker, Context context){
        String fileName = "clicker.save";
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(clicker.toString().getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Clicker loadClickerInternal(Context context){
        String fileName = "clicker.save";
        String clickerStr = null;
        try {
            FileInputStream in = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line).append("\n");
            clickerStr = sb.toString();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseClickerFromClear(clickerStr);
    }

    public static String getClickerSaveStringCrypted(Clicker clicker){
        return ClickerUtil.encryptSave(clicker.toString());
    }
    public static Clicker loadClickerFromCryptedString(String save){
        return parseClickerFromClear(ClickerUtil.decryptSave(save));
    }

    private static Clicker parseClickerFromClear(String clickerStr){
        if(clickerStr == null)
            return new Clicker();
        try{
            String[] sections = clickerStr.split("-----");

            // crepes
            double crepes = Double.parseDouble(sections[0]);

            // flavors
            ArrayList<Integer> flavors = new ArrayList<>();
            for(String str : sections[1].split("\n")){
                if(str == null || str.equals(""))
                    continue;
                flavors.add(Integer.parseInt(str));
            }

            // flavor upgrades
            ArrayList<Integer> flavorUpgrades = new ArrayList<>();
            for(String str : sections[2].split("\n")){
                if(str == null || str.equals(""))
                    continue;
                flavorUpgrades.add(Integer.parseInt(str));
            }

            // supplements upgrades
            ArrayList<Integer> supplementUpgrades = new ArrayList<>();
            for(String str : sections[3].split("\n")){
                if(str == null || str.equals(""))
                    continue;
                supplementUpgrades.add(Integer.parseInt(str));
            }

            // glove upgrades
            int glovesUpgrades = Integer.valueOf(ClickerUtil.removeNewLineChar(sections[4]));

            // golden upgrades
            int goldenUpgrades = Integer.valueOf(ClickerUtil.removeNewLineChar(sections[5]));

            // handmade crepes
            double handMadeCrepes = Double.valueOf(ClickerUtil.removeNewLineChar(sections[6]));

            // made crepes
            double madeCrepes = Double.valueOf(ClickerUtil.removeNewLineChar(sections[7]));

            // max owned crepes
            double maxCrepes = Double.valueOf(ClickerUtil.removeNewLineChar(sections[8]));

            // max owned crepes
            double hoursPlayed = Double.valueOf(ClickerUtil.removeNewLineChar(sections[9]));

            // max owned crepes
            double goldenClicked = Double.valueOf(ClickerUtil.removeNewLineChar(sections[10]));

            return new Clicker(crepes, Util.arrayListInttoArrayInt(flavors), Util.arrayListInttoArrayInt(flavorUpgrades),
                    Util.arrayListInttoArrayBoolean(supplementUpgrades), glovesUpgrades, goldenUpgrades, handMadeCrepes,
                    madeCrepes, maxCrepes, hoursPlayed, goldenClicked);
        }catch (Exception e){
            e.printStackTrace();
            return new Clicker();
        }
    }
}
