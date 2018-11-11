package me.pfrison.polytimeima4.graphics;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.android.ClickerActivity;
import me.pfrison.polytimeima4.android.MainActivity;
import me.pfrison.polytimeima4.graphics.style.Style;
import me.pfrison.polytimeima4.timetable.Day;
import me.pfrison.polytimeima4.timetable.Week;
import me.pfrison.polytimeima4.utils.Util;

class TimeTableDrawer {
    static void drawTimeTable(Week week, ViewGroup rootLayout, final MainActivity mainActivity){
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(mainActivity);
        horizontalScrollView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        LinearLayout weekLayout = new LinearLayout(mainActivity);
        weekLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        weekLayout.setOrientation(LinearLayout.HORIZONTAL);

        // add the hoursLayout
        weekLayout.addView(getHoursLayout(mainActivity));

        // add 5 dayLayouts
        for(int i = 0; i < week.days.length; i++){
            LinearLayout dayLayout = new LinearLayout(mainActivity);
            int width = mainActivity.getResources().getDimensionPixelSize(R.dimen.time_table_day_width);
            dayLayout.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
            dayLayout.setOrientation(LinearLayout.VERTICAL);

            // add the dateTextView
            dayLayout.addView(getDateTextView(i+1, week, mainActivity));

            LinearLayout lessonsLayout = new LinearLayout(mainActivity);
            lessonsLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            lessonsLayout.setOrientation(LinearLayout.VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                lessonsLayout.setBackground(mainActivity.getResources().getDrawable(R.drawable.lessons_layout_border, null));
            else
                lessonsLayout.setBackground(mainActivity.getResources().getDrawable(R.drawable.lessons_layout_border));

            // add the lessons in layout
            lessonsLayout = constructDay(lessonsLayout, week.days[i], mainActivity);

            // add lessonsLayout in dayLayout
            dayLayout.addView(lessonsLayout);

            // secret clicker on saturday
            if(i == week.days.length - 1)
                dayLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainActivity.secretClicks++;
                        if(mainActivity.secretClicks >= 3){
                            Intent intent = new Intent(mainActivity, ClickerActivity.class);
                            mainActivity.startActivity(intent);
                        }
                    }
                });

            weekLayout.addView(dayLayout);
        }

        horizontalScrollView.addView(weekLayout);
        rootLayout.addView(horizontalScrollView);
    }

    private static View createMiddaySeparator(Context context){
        View sep = new View(context);
        sep.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.midday_border_width)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            sep.setBackgroundColor(context.getResources().getColor(R.color.lesson_border, null));
        else
            sep.setBackgroundColor(context.getResources().getColor(R.color.lesson_border));
        return sep;
    }

    private static LinearLayout constructDay(LinearLayout layout, Day day, Context context){
        // is there any lesson ?
        if(day.lessons.length == 0){
            layout.addView(createSpace(8, 12, day.date, context));
            layout.addView(createMiddaySeparator(context));
            layout.addView(createSpace(14, 18, day.date, context));
            return layout;
        }

        // is the first lesson doesn't start at 8 add a space
        if(day.lessons[0].begin > 8){
            // add the space
            layout = addSpace(layout, 8, day.lessons[0].begin, day.date, context);
        }

        // for every lessons
        for(int i=0; i<day.lessons.length; i++){
            int begin = day.lessons[i].begin;
            int end = day.lessons[i].end;
            // ------ add lesson

            // if a lesson is cut by the launch -> split it
            if(begin <= 12 && end >= 14){
                // first bit
                layout.addView(designLessonTV(day, i, begin, 12, context));
                layout.addView(createMiddaySeparator(context));

                //the second bit is generated automatically
                begin = 14;
            }

            // add the lesson
            layout.addView(designLessonTV(day, i, begin, end, context));

            //add midday separator
            if(end == 12)
                layout.addView(createMiddaySeparator(context));

            // ----- add space between the previous lesson and the next (or the end of the table)
            int spaceEnd;
            if(i+1 == day.lessons.length)
                spaceEnd = 18; // last hour
            else
                spaceEnd = day.lessons[i+1].begin;
            layout = addSpace(layout, end, spaceEnd, day.date, context);
        }
        return layout;
    }

    private static LinearLayout addSpace(LinearLayout layout, int begin, int end, String date, Context context){
        // do we need to add a space ?
        if(end - begin > 0
                && (end != 14 || begin != 12)){
            // if a space is cut by the launch -> split it
            if(begin < 12 && end > 14){
                // first bit
                layout.addView(createSpace(begin, 12, date, context));
                layout.addView(createMiddaySeparator(context));

                //the second bit is generated normally
                begin = 14;
            }

            // if the space include the midday, remove it
            if(begin == 12 && end > 14)
                begin = 14;
            else if(begin < 12 && end == 14)
                end = 12;

            // add the space
            layout.addView(createSpace(begin, end, date, context));

            if(end == 14)
                layout.addView(createMiddaySeparator(context));
        }
        return layout;
    }

    private static View createSpace(int spaceBegin, int spaceEnd, String date, Context context){
        int weight = spaceEnd - spaceBegin;
        View space = new View(context);
        space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight));
        space.setBackground(getLessonBackground(spaceBegin, spaceEnd, date, context));
        return space;
    }

    private static TextView getDateTextView(int index, Week week, Context context){
        if(index == 0){
            TextView space = new TextView(context);
            space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            space.setText("");
            return space;
        }

        TextView dateTV = new TextView(context);
        dateTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dateTV.setGravity(Gravity.CENTER);
        dateTV.setText(week.days[index-1].date);
        return dateTV;
    }

    private static LinearLayout getHoursLayout(Context context){
        LinearLayout hoursLayout = new LinearLayout(context);
        hoursLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        hoursLayout.setOrientation(LinearLayout.VERTICAL);
        hoursLayout.setPadding(20, 10, 20, -10);

        hoursLayout.addView(getDateTextView(0, null, context));

        int hourBegin = 8;
        for(int i=0; i<8; i++){
            TextView hourTV = new TextView(context);
            hourTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            hourTV.setGravity(Gravity.CENTER_HORIZONTAL);
            hourTV.setText(String.valueOf(hourBegin) + "h");
            hoursLayout.addView(hourTV);

            hourBegin++;
            if(hourBegin == 12)
                hourBegin = 14;
        }
        return hoursLayout;
    }

    private static TextView designLessonTV(Day day, int lessonIndex, int begin, int end, Context context){
        int weight = end - begin;

        TextView lessonTV = new TextView(context);
        lessonTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, weight));

        Spanned spanned = Html.fromHtml("<b>" + day.lessons[lessonIndex].name + "</b><br>" + day.lessons[lessonIndex].room);
        lessonTV.setText(spanned);

        lessonTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        lessonTV.setBackground(getLessonBackground(begin, end, day.date, context));

        lessonTV.setGravity(Gravity.CENTER);
        return lessonTV;
    }

    private static Drawable getLessonBackground(int begin, int end, String date, Context context){
        // highlight the lesson ?
        Calendar today = Calendar.getInstance();
        Calendar cal = Util.stringToCalendar(date);
        // calendar is not null and same date ?
        if(cal != null
                && today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                && today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)){
            // is the lesson is next or current lesson ?
            boolean active;
            if(begin <= today.get(Calendar.HOUR_OF_DAY)
                    && today.get(Calendar.HOUR_OF_DAY) < end)
                active = true;
            else if(begin == 8 && today.get(Calendar.HOUR_OF_DAY) < 8)
                active = true;
            else if(12 <= today.get(Calendar.HOUR_OF_DAY)
                    && today.get(Calendar.HOUR_OF_DAY) < 14
                    && begin == 14)
                active = true;
            else
                active = false;
            boolean firstOfRow = begin == 8;
            return Style.createLessonActiveBackground(active, context, firstOfRow);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                return context.getResources().getDrawable(R.drawable.lesson_border, null);
            else
                return context.getResources().getDrawable(R.drawable.lesson_border);
        }
    }
}
