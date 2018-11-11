package me.pfrison.polytimeima4.clicker;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.Random;

import me.pfrison.polytimeima4.graphics.Animator;
import me.pfrison.polytimeima4.utils.ClickerUtil;

public class ClickerHandMadePopups{
    private static final int MAX_POPUPS = 10;

    private Clicker clicker;
    private HandMadePopup[] popups;
    private int wheel = 0;

    public ClickerHandMadePopups(Context context, Clicker clicker, ViewGroup root) {
        this.clicker = clicker;

        popups = new HandMadePopup[MAX_POPUPS];
        for(int i=0; i<MAX_POPUPS; i++) {
            popups[i] = new HandMadePopup(context);
            root.addView(popups[i]);
        }
    }

    public void click(float posX, float posY){
        popups[wheel].updateText(clicker.getCrepesPerClick());
        popups[wheel].setLocation((int) posX, (int) posY);
        Animator.animateFadeAway(popups[wheel]);

        wheel++;
        if(wheel >= MAX_POPUPS)
            wheel = 0;
    }

    private class HandMadePopup extends AppCompatTextView {
        private HandMadePopup(Context context) {
            super(context);
            setLocation(0, 0);
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            setTypeface(null, Typeface.BOLD);
            setAlpha(0f);
        }

        public void updateText(double crepes){
            setText("+ " + ClickerUtil.humanReadableNumbersCounter(crepes, getContext()));
        }

        public void setLocation(int posX, int posY){
            Random random = new Random();
            int randX = (random.nextBoolean() ? -1 : 1) * random.nextInt(20);
            int randY = (random.nextBoolean() ? -1 : 1) * random.nextInt(20);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(posX + randX, posY + randY, 0, 0);
            setLayoutParams(params);
        }
    }
}
