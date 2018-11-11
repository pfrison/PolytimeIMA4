package me.pfrison.polytimeima4.android;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.pfrison.polytimeima4.achievements.Achievement;
import me.pfrison.polytimeima4.achievements.AchievementPopup;
import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.graphics.style.Style;
import me.pfrison.polytimeima4.utils.Util;

public class AchievementActivity extends AppCompatActivity {
    private AchievementPopup achievementPopup;

    private TextView clubDesc;
    private ImageView clubImg;
    private Bitmap clubBitmap;

    private TextView knowledgeDesc;
    private ImageView knowledgeImg;
    private Bitmap knowledgeBitmap;

    private TextView rootTitleTV;
    private String rootTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Style.getThemeResIdFromPreferences(this));
        setTitle(getResources().getString(R.string.main_menu_achievements));
        setContentView(R.layout.activity_achievement);

        // achievements
        achievementPopup = new AchievementPopup(findViewById(R.id.achievements_popup_root), this);

        // title
        rootTitleTV = findViewById(R.id.achievements_title);
        rootTitle = rootTitleTV.getText().toString();
        rootTitleTV.setText(rootTitle + " (" + String.valueOf(Achievement.countAchievementDone())
                + "/" + String.valueOf(Achievement.achievements.length) + ")");

        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            color = this.getResources().getColor(Style.getColorPrimaryResIdFromPreferences(this), null);
        else
            color = this.getResources().getColor(Style.getColorPrimaryResIdFromPreferences(this));

        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
        Paint paint = new Paint();
        paint.setColorFilter(filter);

        // build achievements
        LinearLayout rootLayout = findViewById(R.id.achievements_root);
        for(final Achievement achievement : Achievement.achievements){
            LinearLayout achievementLayout = new LinearLayout(this);
            achievementLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            achievementLayout.setOrientation(LinearLayout.HORIZONTAL);

            // image
            ImageView imageIV = new ImageView(this);
            int imageSize = getResources().getDimensionPixelSize(R.dimen.achievement_image_size);
            imageIV.setLayoutParams(new LinearLayout.LayoutParams(imageSize, imageSize));
            if(achievement.isDone()){
                // paint icon to the right color
                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), achievement.imageResource)
                        .copy(Bitmap.Config.ARGB_8888, true);
                new Canvas(bitmap).drawBitmap(bitmap, 0, 0, paint);
                imageIV.setImageBitmap(bitmap);
            }else
                imageIV.setImageResource(R.drawable.ic_achievement_not_done_gray_24dp);
            achievementLayout.addView(imageIV);

            // text layout
            LinearLayout textLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(getResources().getDimensionPixelSize(R.dimen.achievement_popup_margin_text));
            textLayout.setLayoutParams(params);
            textLayout.setOrientation(LinearLayout.VERTICAL);

            // title
            TextView titleTV = new TextView(this);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, getResources().getDimensionPixelSize(R.dimen.achievement_popup_margin_text), 0, 0);
            titleTV.setLayoutParams(params);
            titleTV.setText(achievement.title);
            titleTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            titleTV.setTypeface(null, Typeface.BOLD);
            textLayout.addView(titleTV);

            // description
            TextView descTV = new TextView(this);
            descTV.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            if(achievement.isDone())
                descTV.setText(achievement.description);
            else
                descTV.setText("?????");
            textLayout.addView(descTV);

            achievementLayout.addView(textLayout);
            rootLayout.addView(achievementLayout);

            // add listeners
            achievementLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {clickActions(achievement);}
            });

            // get all we need to update achievement
            if(!achievement.isDone() && achievement.getId() == Achievement.ID_CLUB) {
                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), achievement.imageResource)
                        .copy(Bitmap.Config.ARGB_8888, true);
                new Canvas(bitmap).drawBitmap(bitmap, 0, 0, paint);
                clubBitmap = bitmap;
                clubImg = imageIV;
                clubDesc = descTV;
            }else if(!achievement.isDone() && achievement.getId() == Achievement.ID_KNOWLEDGE) {
                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), achievement.imageResource)
                        .copy(Bitmap.Config.ARGB_8888, true);
                new Canvas(bitmap).drawBitmap(bitmap, 0, 0, paint);
                knowledgeBitmap = bitmap;
                knowledgeImg = imageIV;
                knowledgeDesc = descTV;
            }
        }
    }

    private void analyseResponse(String rep, Achievement achievement, DialogInterface dialog){
        switch (achievement.getId()){
            case Achievement.ID_CLUB:
                if(rep.equalsIgnoreCase("CREP")) {
                    dialog.dismiss();
                    Achievement.achievements[Achievement.ID_CLUB].setDone(achievementPopup);
                    clubImg.setImageBitmap(clubBitmap);
                    clubDesc.setText(achievement.description);
                    rootTitleTV.setText(rootTitle + " (" + String.valueOf(Achievement.countAchievementDone())
                            + "/" + String.valueOf(Achievement.achievements.length) + ")");
                }else{
                    dialog.cancel();
                    dialogWrong();
                }
                break;
            case Achievement.ID_KNOWLEDGE:
                if(rep.equals(Achievement.secretAchievementPassword)) {
                    dialog.dismiss();
                    dialogWrongCustom(getResources().getString(R.string.not_so_easy));
                }else if(rep.equals(Util.strangeCode(Achievement.secretAchievementPassword))){
                    dialog.dismiss();
                    Achievement.achievements[Achievement.ID_KNOWLEDGE].setDone(achievementPopup);
                    knowledgeImg.setImageBitmap(knowledgeBitmap);
                    knowledgeDesc.setText(achievement.description);
                    rootTitleTV.setText(rootTitle + " (" + String.valueOf(Achievement.countAchievementDone())
                            + "/" + String.valueOf(Achievement.achievements.length) + ")");
                }else{
                    dialog.cancel();
                    dialogWrong();
                }
                break;
        }
    }

    private void clickActions(Achievement achievement){
        if(achievement.isDone())
            return;
        if(achievement.getId() != Achievement.ID_CLUB
                && achievement.getId() != Achievement.ID_KNOWLEDGE)
            return;

        dialogClub(achievement);
    }

    private void dialogClub(final Achievement achievement){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(achievement.title);
        builder.setMessage(getResources().getString(R.string.enter_response));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                analyseResponse(input.getText().toString(), achievement, dialog);
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
        });

        builder.show();
    }

    private void dialogWrong(){
        dialogWrongCustom(getResources().getString(R.string.search_more));
    }
    private void dialogWrongCustom(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.wrong));
        builder.setMessage(message);

        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
        });

        builder.show();
    }
}
