package me.pfrison.polytimeima4.clicker.golden;

import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.clicker.Clicker;

public class ClickerGoldenEffect {
    public static final int TYPE_CREPE_PER_SECONDS = 0;
    public static final int TYPE_CREPE_PER_CLICKS = 1;

    private int duration;
    public int type;
    public double multiplier;

    public ClickerGoldenEffect(int duration, double multiplier, final int type, final Clicker clicker,
                        final ViewGroup goldenEffectSides, final ImageView clickerLight1, final ImageView clickerLight2){
        this.duration = duration;
        this.multiplier = multiplier;
        this.type = type;

        // visual effects
        goldenEffectSides.setAlpha(0.6f);
        clickerLight1.setImageResource(R.mipmap.clicker_light_1_golden);
        clickerLight2.setImageResource(R.mipmap.clicker_light_2_golden);

        // add itself
        clicker.clickerEffects.add(this);

        // update cps or cpc
        if (type == TYPE_CREPE_PER_SECONDS)
            clicker.updateCrepesPerSeconds();
        else if (type == TYPE_CREPE_PER_CLICKS)
            clicker.updateCrepesPerClick();

        // remove itself after its end
        final ClickerGoldenEffect finalThis = this;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // remove itself
                for (int i=0; i<clicker.clickerEffects.size(); i++){
                    if(finalThis.equals(clicker.clickerEffects.get(i))) {
                        clicker.clickerEffects.remove(i);

                        // update cps and cpc
                        clicker.updateCrepesPerSeconds();
                        clicker.updateCrepesPerClick();
                    }
                }

                // remove visual effects
                if(clicker.clickerEffects.size() == 0){
                    goldenEffectSides.setAlpha(0f);
                    clickerLight1.setImageResource(R.mipmap.clicker_light_1);
                    clickerLight2.setImageResource(R.mipmap.clicker_light_2);
                }
            }
        }, duration);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ClickerGoldenEffect))
            return false;

        ClickerGoldenEffect effect = (ClickerGoldenEffect) obj;
        return effect.duration == this.duration
                && effect.multiplier == this.multiplier
                && effect.type == this.type;
    }
}
