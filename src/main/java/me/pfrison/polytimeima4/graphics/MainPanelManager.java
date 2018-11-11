package me.pfrison.polytimeima4.graphics;

import android.app.Activity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.android.MainActivity;
import me.pfrison.polytimeima4.timetable.TimeTable;
import me.pfrison.polytimeima4.utils.TimeTableUtil;

public class MainPanelManager {
    public static TimeTable timeTable;
    private static int showedWeek;

    private static final int PREV = 0;
    private static final int CURRENT = 1;
    private static final int NEXT = 2;

    public static final int KEEP = 0;
    public static final int REPLACE = 1;

    public static final int LOAD = 0;
    public static final int DOWNLOAD = 1;

    private static void clearMainPanel(Activity activity){
        FrameLayout root = activity.findViewById(R.id.main_panel);
        root.removeAllViews();
    }

    private static void refreshTimeTable(final MainActivity activity, int animation){
        final FrameLayout root = activity.findViewById(R.id.main_panel);

        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                // save scroll state
                if( !(root.getChildAt(0) instanceof HorizontalScrollView) || root.getChildAt(0) == null)
                    return;
                HorizontalScrollView scrollView = (HorizontalScrollView) root.getChildAt(0);
                final int posY = scrollView.getScrollY();
                final int posX = scrollView.getScrollX();

                // refresh timetable
                clearMainPanel(activity);
                TimeTableDrawer.drawTimeTable(
                        MainPanelManager.timeTable.weeks[MainPanelManager.showedWeek],
                        root, activity);

                // restore scroll state
                if( !(root.getChildAt(0) instanceof HorizontalScrollView) || root.getChildAt(0) == null)
                    return;
                final HorizontalScrollView finalScrollView = (HorizontalScrollView) root.getChildAt(0);
                finalScrollView.post(new Runnable() {
                    @Override
                    public void run() {finalScrollView.scrollTo(posX, posY);}
                });
            }
        };

        switch (animation){
            case PREV:
                Animator.animateLeft(root, refresh); break;
            case CURRENT:
                Animator.animateOutIn(root, refresh); break;
            case NEXT:
                Animator.animateRight(root, refresh); break;
        }
    }



    public static void setTimeTable(final TimeTable timeTable, final MainActivity activity, int command){
        MainPanelManager.timeTable = timeTable;
        MainPanelManager.showedWeek = TimeTableUtil.getCurrentWeekInt(timeTable, true);

        final FrameLayout root = activity.findViewById(R.id.main_panel);

        Runnable setTimeTable = new Runnable() {
            @Override
            public void run() {
                clearMainPanel(activity);
                TimeTableDrawer.drawTimeTable(timeTable.weeks[showedWeek], root, activity);
            }
        };

        switch (command){
            case LOAD:
                setTimeTable.run();
                Animator.animateIn(root);
                break;
            case DOWNLOAD:
                setTimeTable.run();
                break;
        }

        activity.detectAchievementBoring(timeTable);
    }

    public static void prevWeek(MainActivity activity){
        if(MainPanelManager.timeTable == null) return;
        if(MainPanelManager.showedWeek - 1 < 0)
            return;
        MainPanelManager.showedWeek--;
        refreshTimeTable(activity, PREV);
    }

    public static void currentWeek(MainActivity activity){
        if(MainPanelManager.timeTable == null) return;
        MainPanelManager.showedWeek = TimeTableUtil.getCurrentWeekInt(MainPanelManager.timeTable, true);
        refreshTimeTable(activity, CURRENT);
    }

    public static void nextWeek(MainActivity activity){
        if(MainPanelManager.timeTable == null) return;
        if(MainPanelManager.showedWeek + 1 >= MainPanelManager.timeTable.weeks.length)
            return;
        MainPanelManager.showedWeek++;
        refreshTimeTable(activity, NEXT);
    }

    public static void printMessage(String message, int command, Activity activity){
        switch (command){
            case KEEP:
                if(MainPanelManager.timeTable != null)
                    return;
                break;
            case REPLACE:
                // do nothing
                break;
        }

        MainPanelManager.timeTable = null;
        MainPanelManager.showedWeek = 0;

        FrameLayout root = activity.findViewById(R.id.main_panel);
        clearMainPanel(activity);

        TextView messageTV = new TextView(activity);
        messageTV.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        messageTV.setGravity(Gravity.CENTER);
        messageTV.setText(message);

        root.addView(messageTV);
    }
}
