package me.pfrison.polytimeima4.clicker.golden;

import android.app.Activity;
import android.graphics.Point;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.clicker.Clicker;
import me.pfrison.polytimeima4.clicker.ClickerMechanics;
import me.pfrison.polytimeima4.graphics.Animator;
import me.pfrison.polytimeima4.graphics.ClickerGraphics;

public class ClickerGolden {
    private Activity activity;
    private Golden golden;
    private Clicker clicker;
    private ImageView goldenCrepe;
    private int sizeGolden;
    private RelativeLayout.LayoutParams params;
    private boolean go;

    public ClickerGolden(Activity activity, Clicker clicker, ViewGroup root,
                         ViewGroup goldenEffectSides, ImageView clickerLight1, ImageView clickerLight2,
                         TextView goldenText){
        // generate the first golden
        this.golden = new Golden(clicker);
        this.clicker = clicker;
        this.activity = activity;

        // build the image view
        this.goldenCrepe = new ImageView(activity);
        this.sizeGolden = activity.getResources().getDimensionPixelSize(R.dimen.clicker_golden_crepe_size);
        this.params = new RelativeLayout.LayoutParams(sizeGolden, sizeGolden);
        params.setMargins(randomLocation(sizeGolden, activity), randomLocation(sizeGolden, activity), 0, 0);
        goldenCrepe.setLayoutParams(params);
        goldenCrepe.setImageResource(R.mipmap.crepe_golden);
        root.addView(goldenCrepe);

        this.go = true;

        // initialize animations and click listener
        initialize(goldenEffectSides, clickerLight1, clickerLight2, goldenText);
    }

    private void initialize(final ViewGroup goldenEffectSides, final ImageView clickerLight1, final ImageView clickerLight2, final TextView goldenText){
        if(!go)
            return;

        // regenerate golden
        golden = new Golden(clicker);

        goldenCrepe.clearAnimation();
        goldenCrepe.setAlpha(0f);
        goldenCrepe.setClickable(false);

        // random location
        params.setMargins(randomLocation(sizeGolden, activity), randomLocation(sizeGolden, activity), 0, 0);
        goldenCrepe.setLayoutParams(params);

        // spawn timer
        Handler handlerSpawn = new Handler();
        handlerSpawn.postDelayed(new Runnable() {
            @Override
            public void run() {
                goldenCrepe.setClickable(true);

                // build animations
                // duration is common to scale and alpha animations
                double duration = (double) golden.timeToDespawn * 0.2; // 20% of golden life span
                final AnimationSet[] animationSets = buildAnimationsSets(duration);

                // add spawn animation
                goldenCrepe.setAlpha(1f); // alpha animation is relative due to animationSet
                goldenCrepe.startAnimation(animationSets[0]);

                // despawn animation
                final Runnable despawnAnimation = new Runnable() {
                    @Override
                    public void run() {
                        goldenCrepe.startAnimation(animationSets[1]);
                    }
                };
                final Handler handlerDespawnAnimation = new Handler();
                handlerDespawnAnimation.postDelayed(despawnAnimation, (long) (golden.timeToDespawn - duration));

                // despawn
                final Runnable despawn = new Runnable() {
                    @Override
                    public void run() {
                        // reinitialize
                        initialize(goldenEffectSides, clickerLight1, clickerLight2, goldenText);
                    }
                };
                final Handler handlerDespawn = new Handler();
                handlerDespawn.postDelayed(despawn, golden.timeToDespawn);

                // click listener
                goldenCrepe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double param = ClickerMechanics.clickGolden(golden, clicker, goldenEffectSides, clickerLight1, clickerLight2);

                        // golden text
                        goldenText.setText(ClickerGraphics.getGoldenText(golden, param, activity));
                        Animator.animateAppearUp(goldenText);

                        // cancel despawn
                        handlerDespawn.removeCallbacks(despawn);
                        handlerDespawnAnimation.removeCallbacks(despawnAnimation);

                        // reinitialize
                        initialize(goldenEffectSides, clickerLight1, clickerLight2, goldenText);
                    }
                });
            }
        }, golden.timeToSpawn);
    }

    public void stop(){this.go = false;}

    private int randomLocation(int sizeGolden, Activity activity){
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        double maxLocation = point.x - sizeGolden;

        // 5% margin on each side (= 10% in total)
        maxLocation = maxLocation - maxLocation*0.10;
        return new Random().nextInt((int) maxLocation) + (int) (maxLocation*0.05);
    }

    private AnimationSet[] buildAnimationsSets(double duration){
        AnimationSet animationSetSpawn = new AnimationSet(false);
        AnimationSet animationSetDespawn = new AnimationSet(false);

        // wiggle animation
        Animation wiggleAnimation = AnimationUtils.loadAnimation(activity, R.anim.wiggle);
        animationSetSpawn.addAnimation(wiggleAnimation);
        animationSetDespawn.addAnimation(wiggleAnimation);

        // appear animation scale
        Animation appearAnimation = new ScaleAnimation(
                0f, 1f, // X from / to
                0f, 1f, // Y from / to
                Animation.RELATIVE_TO_SELF, 0.5f, // pivot X
                Animation.RELATIVE_TO_SELF, 0.5f); // pivot Y
        appearAnimation.setFillAfter(true);
        appearAnimation.setDuration((long) duration);
        animationSetSpawn.addAnimation(appearAnimation);

        // appear animation alpha
        Animation animationAlphaSpawn = new AlphaAnimation(0f, 1f);
        animationAlphaSpawn.setDuration((long) duration);
        animationAlphaSpawn.setFillAfter(true);
        animationSetSpawn.addAnimation(animationAlphaSpawn);

        // disappear animation scale
        Animation disappearAnimation = new ScaleAnimation(
                1f, 0f, // X from / to
                1f, 0f, // Y from / to
                Animation.RELATIVE_TO_SELF, 0.5f, // pivot X
                Animation.RELATIVE_TO_SELF, 0.5f); // pivot Y
        disappearAnimation.setFillAfter(true);
        disappearAnimation.setDuration((long) duration);
        animationSetDespawn.addAnimation(disappearAnimation);

        // disappear animation alpha
        Animation animationAlphaDespawn = new AlphaAnimation(1f, 0f);
        animationAlphaDespawn.setDuration((long) duration);
        animationAlphaDespawn.setFillAfter(true);
        animationSetDespawn.addAnimation(animationAlphaDespawn);

        return new AnimationSet[]{animationSetSpawn, animationSetDespawn};
    }
}
