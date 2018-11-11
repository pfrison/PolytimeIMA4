package me.pfrison.polytimeima4.android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.pfrison.polytimeima4.achievements.Achievement;
import me.pfrison.polytimeima4.achievements.AchievementPopup;
import me.pfrison.polytimeima4.R;
import me.pfrison.polytimeima4.clicker.Clicker;
import me.pfrison.polytimeima4.clicker.golden.ClickerGolden;
import me.pfrison.polytimeima4.clicker.ClickerHandMadePopups;
import me.pfrison.polytimeima4.clicker.ClickerIngredients;
import me.pfrison.polytimeima4.clicker.ClickerMechanics;
import me.pfrison.polytimeima4.clicker.ClickerSaverLoader;
import me.pfrison.polytimeima4.clicker.upgrade.ClickerUpgrades;
import me.pfrison.polytimeima4.graphics.Animator;
import me.pfrison.polytimeima4.graphics.ClickerGraphics;
import me.pfrison.polytimeima4.utils.ClickerUtil;

public class ClickerActivity extends AppCompatActivity {
    private ImageView clickerButton;
    private TextView moneyCounter;
    private TextView moneyCounterPerSeconds;
    private Clicker clicker;

    private ClickerIngredients clickerIngredients;
    private ClickerUpgrades clickerUpgrades;
    private ClickerGolden clickerGolden;

    private boolean run = false;
    private Handler waitChecker;
    private final int waitDelay = 50;
    private long lastWait;

    private AchievementPopup achievementPopup;

    private int achievementWheel = 0;
    private Intent batteryStatus;
    private final int batteryDelay = 10000;
    private int saveWheel = 1;
    private final int saveDelay = 120000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ClickerTheme);
        setTitle(getResources().getString(R.string.main_menu_clicker));
        setContentView(R.layout.activity_clicker);

        // achievements
        this.achievementPopup = new AchievementPopup(findViewById(R.id.achievements_popup_root), this);
        batteryStatus = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        // animate lights
        ImageView clickerLight1 = findViewById(R.id.clicker_light_1);
        ImageView clickerLight2 = findViewById(R.id.clicker_light_2);
        clickerLight1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.infinite_rotation_1));
        clickerLight2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.infinite_rotation_2));

        // load save
        clicker = ClickerSaverLoader.loadClickerInternal(this);

        // crepe hand made popups
        ViewGroup clickerRootViewGroup = findViewById(R.id.clicker_root);
        final ClickerHandMadePopups clickerHandMadePopups = new ClickerHandMadePopups(this, clicker,
                clickerRootViewGroup);

        // money counter
        moneyCounter = findViewById(R.id.clicker_counter);
        moneyCounterPerSeconds = findViewById(R.id.clicker_counter_ps);
        updateCounters();

        // crepe button
        clickerButton = findViewById(R.id.clicker_crepe);
        clickerButton.setImageResource(ClickerGraphics.getFlavorsResId(clicker.level));

        //noinspection AndroidLintClickableViewAccessibility
        clickerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Animator.animateBump(clickerButton);
                        ClickerMechanics.addClick(clicker);
                        updateCounters();

                        clickerHandMadePopups.click(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // build ingredients list
        clickerIngredients = new ClickerIngredients(this, clicker,
                (LinearLayout) findViewById(R.id.clicker_ingredients_root), clickerButton, achievementPopup);

        // build upgrades list
        clickerUpgrades = new ClickerUpgrades(this, clicker,
                (LinearLayout) findViewById(R.id.clicker_upgrades_root));

        // golden crepes
        clickerGolden = new ClickerGolden(this, clicker,
                clickerRootViewGroup,
                (ViewGroup) findViewById(R.id.clicker_golden_effect_sides),
                clickerLight1,
                clickerLight2,
                (TextView) findViewById(R.id.clicker_golden_text));

        // discover clicker achievement
        Achievement.achievements[Achievement.ID_CLICKER].setDone(achievementPopup);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clickerGolden.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        run = false;
        ClickerSaverLoader.saveClickerInternal(clicker, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // build waitChecker
        run = true;
        lastWait = System.currentTimeMillis();
        waitChecker = new Handler();
        waitChecker.postDelayed(new Runnable() {
            @Override
            public void run() {
                // achievements
                if(!Achievement.achievements[Achievement.ID_CLICKER_BATTERY].isDone()
                        || !Achievement.achievements[Achievement.ID_CLICKER_NEVERCLICK].isDone()){
                    if(achievementWheel == 0){
                        // battery achievement
                        if(!Achievement.achievements[Achievement.ID_CLICKER_BATTERY].isDone()){
                            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                            double batteryPct = (double) level / (double) scale;
                            if(batteryPct <= 0.05d)
                                Achievement.achievements[Achievement.ID_CLICKER_BATTERY].setDone(achievementPopup);
                        }

                        // battery achievement
                        if(!Achievement.achievements[Achievement.ID_CLICKER_NEVERCLICK].isDone()
                                && clicker.getMadeCrepes() >= 1000000
                                && clicker.getHandMadeCrepes() == 0){
                            Achievement.achievements[Achievement.ID_CLICKER_NEVERCLICK].setDone(achievementPopup);
                        }
                    }
                    achievementWheel++;
                    if(achievementWheel >= (double) batteryDelay / (double) waitDelay)
                        achievementWheel = 0;
                }
                // auto save
                if(saveWheel == 0)
                    ClickerSaverLoader.saveClickerInternal(clicker, getBaseContext());
                saveWheel++;
                if(saveWheel >= (double) saveDelay / (double) waitDelay)
                    saveWheel = 0;

                // add time played
                clicker.addTimePlayed((int) (System.currentTimeMillis() - lastWait));

                // add crepes
                ClickerMechanics.addWait(clicker, (int) (System.currentTimeMillis() - lastWait));
                updateCounters();
                lastWait = System.currentTimeMillis();

                // can buy ?
                recheckAllCanBuy();

                // repeat
                if(run)
                    waitChecker.postDelayed(this, waitDelay);
            }
        }, waitDelay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clicker_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder alertDialog;
        final EditText input;
        switch (item.getItemId()) {
            case R.id.clicker_menu_stats:
                Intent intent = new Intent(this, ClickerStatsActivity.class);
                startActivity(intent);
                return true;
            case R.id.clicker_menu_wipe_save:
                alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle(getResources().getString(R.string.clicker_wipe_save_popup_title));
                alertDialog.setMessage(getResources().getString(R.string.clicker_wipe_save_popup_message));

                alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // wipe save
                        clicker = new Clicker();
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
                });

                alertDialog.show();
                return true;
            case R.id.clicker_menu_export_save:
                alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle(getResources().getString(R.string.clicker_export_save_popup_title));
                alertDialog.setMessage(getResources().getString(R.string.clicker_export_save_popup_message));

                final String save = ClickerSaverLoader.getClickerSaveStringCrypted(clicker);
                input = new EditText(this);
                input.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                input.setText(save);
                alertDialog.setView(input);

                alertDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
                });
                alertDialog.setNegativeButton(getResources().getString(R.string.copy_to_clipboard), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("ClickerSave", save);
                        if (clipboard != null) {
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.copy_to_clipboard_success), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

                alertDialog.show();
                return true;
            case R.id.clicker_menu_import_save:
                alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle(getResources().getString(R.string.clicker_import_save_popup_title));
                alertDialog.setMessage(getResources().getString(R.string.clicker_import_save_popup_message));

                input = new EditText(this);
                input.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                alertDialog.setView(input);

                alertDialog.setPositiveButton(getResources().getString(R.string.load), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clicker = ClickerSaverLoader.loadClickerFromCryptedString(input.getText().toString());
                        finish();
                        startActivity(getIntent());
                    }
                });
                alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {dialog.cancel();}
                });

                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateCounters(){
        moneyCounter.setText(ClickerUtil.humanReadableNumbersCounter(clicker.getCrepes(), this));
        moneyCounterPerSeconds.setText(ClickerUtil.humanReadableNumbersCounterPS(clicker.getCrepesPerSeconds(), this));
    }

    public void recheckAllCanBuy(){
        clickerIngredients.recheckAllCanBuy();
        clickerUpgrades.recheckAllCanBuy();
    }
}
