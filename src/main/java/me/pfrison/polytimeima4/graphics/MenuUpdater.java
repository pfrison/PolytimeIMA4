package me.pfrison.polytimeima4.graphics;

import android.content.Context;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;

import me.pfrison.polytimeima4.R;

public class MenuUpdater {
    public static void setOnline(Menu menu, Context context){
        if(menu == null)
            return;

        MenuItem item = menu.findItem(R.id.main_menu_status);
        item.setVisible(true);
        item.setTitle(context.getResources().getString(R.string.main_menu_online));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_cloud_done_white_24dp, null));
        else
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_cloud_done_white_24dp));
    }

    public static void setDownloading(Menu menu, Context context){
        if(menu == null)
            return;

        MenuItem item = menu.findItem(R.id.main_menu_status);
        item.setVisible(true);
        item.setTitle(context.getResources().getString(R.string.main_menu_downloading));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_cloud_download_white_24dp, null));
        else
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_cloud_download_white_24dp));
    }

    public static void setOffline(Menu menu, Context context){
        if(menu == null)
            return;

        MenuItem item = menu.findItem(R.id.main_menu_status);
        item.setVisible(true);
        item.setTitle(context.getResources().getString(R.string.main_menu_offline));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_cloud_off_white_24dp, null));
        else
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_cloud_off_white_24dp));
    }

    public static void setReadError(Menu menu, Context context){
        if(menu == null)
            return;

        MenuItem item = menu.findItem(R.id.main_menu_status);
        item.setVisible(true);
        item.setTitle(context.getResources().getString(R.string.main_menu_read_error));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_warning_white_24dp, null));
        else
            item.setIcon(context.getResources().getDrawable(R.drawable.ic_warning_white_24dp));
    }


}
