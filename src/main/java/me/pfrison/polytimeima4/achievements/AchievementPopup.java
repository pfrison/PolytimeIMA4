package me.pfrison.polytimeima4.achievements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.Animator;
import me.pfrison.polytimeima4.graphics.style.Style;

public class AchievementPopup {
    private View layout;
    private Context context;

    private ArrayList<Achievement> achievementsQueue = new ArrayList<>();

    public AchievementPopup(View layout, Context context){
        this.layout = layout;
        this.context = context;
        Style.styliseAchievementPopup(layout, context);
    }

    Context getContext(){return this.context;}

    void appear(Achievement achievement) {
        // add achievement to the queue
        achievementsQueue.add(achievement);
        if(achievementsQueue.size() != 1)
            return;

        TextView title = layout.findViewById(R.id.achievements_popup_title);
        TextView desc = layout.findViewById(R.id.achievements_popup_desc);
        ImageView img = layout.findViewById(R.id.achievements_popup_img);

        title.setText(achievement.title);
        desc.setText(achievement.description);

        //apply color filter to image
        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), achievement.imageResource)
                .copy(Bitmap.Config.ARGB_8888, true);

        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            color = this.context.getResources().getColor(Style.getColorPrimaryResIdFromPreferences(this.context), null);
        else
            color = this.context.getResources().getColor(Style.getColorPrimaryResIdFromPreferences(this.context));

        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        Paint paint = new Paint();
        paint.setColorFilter(filter);
        new Canvas(bitmap).drawBitmap(bitmap, 0, 0, paint);

        img.setImageBitmap(bitmap);

        // catch onClick event so the user can't click what's behind the layout
        layout.setClickable(true);
        final Runnable next = new Runnable() {
            @Override
            public void run() {
                // are any achievement left in the queue ?
                achievementsQueue.remove(0);
                if(achievementsQueue.size() != 0) {
                    Achievement nextAchivement = achievementsQueue.get(0);
                    achievementsQueue.remove(0);
                    // play the next achievement
                    appear(nextAchivement);
                }else
                    layout.setClickable(false);
            }
        };
        Animator.animateAchievement(layout, next);
    }
}
