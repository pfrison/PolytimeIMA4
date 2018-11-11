package me.pfrison.polytimeima4.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Util {
    public static String calendarToString(Calendar cal){
        String day = new SimpleDateFormat("EE", Locale.getDefault()).format(cal.getTime());
        // capitalize day
        day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();
        String date = DateFormat.getDateInstance().format(cal.getTime());
        return day + " " + date;
    }

    public static Calendar stringToCalendar(String date){
        String[] dateSplit = date.split(" ", 2);
        if(dateSplit.length != 2)
            return null;

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DateFormat.getDateInstance().parse(dateSplit[1]));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return cal;
    }

    public static int[] arrayListInttoArrayInt(ArrayList<Integer> arrayList){
        int[] array = new int[arrayList.size()];
        for(int i=0; i<arrayList.size(); i++)
            array[i] = arrayList.get(i);
        return array;
    }

    public static boolean[] arrayListInttoArrayBoolean(ArrayList<Integer> arrayList){
        boolean[] array = new boolean[arrayList.size()];
        for(int i=0; i<arrayList.size(); i++)
            array[i] = arrayList.get(i) >= 1;
        return array;
    }

    public static String strangeCode(String string){
        // Hello again ! Well, have fun searching the true code !

        char[] jkfdjh=string.toCharArray();
        for(int dfsk=1;dfsk<(jkfdjh.length/3)-2;dfsk++){
        for(int lds5d=(jkfdjh.length/3)*2;lds5d>0;lds5d--){for(int uoj=0;uoj<9;uoj++){
        char temp=jkfdjh[dfsk];
        jkfdjh[0]=jkfdjh[jkfdjh.length-1];
        jkfdjh[dfsk]=jkfdjh[jkfdjh.length-lds5d-1];jkfdjh[2]=jkfdjh[jkfdjh.length-1];
        jkfdjh[jkfdjh.length - dfsk - 1] = temp;}}}
        for(int df86d=0; df86d<jkfdjh.length; df86d=df86d+2)if(jkfdjh[df86d]==Character.toLowerCase(jkfdjh[df86d]))jkfdjh[jkfdjh.length-df86d-1]=Character
        .toUpperCase(jkfdjh[df86d]);else{jkfdjh[jkfdjh.length-df86d-1]=Character.toLowerCase(jkfdjh[df86d]);}
        StringBuilder fgh654 = new StringBuilder();
        for(char dg686op:jkfdjh)fgh654.append(dg686op);
        return fgh654.toString();
    }
}
