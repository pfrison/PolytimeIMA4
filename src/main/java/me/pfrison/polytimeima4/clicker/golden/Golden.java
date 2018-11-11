package me.pfrison.polytimeima4.clicker.golden;

import java.util.Random;

import me.pfrison.polytimeima4.clicker.Clicker;
import me.pfrison.polytimeima4.clicker.ClickerStatics;

public class Golden {
    public static final int TYPE_INSTANT_GAIN = 0;
    public static final int TYPE_LIGHT_MULTIPLIER = 1;
    public static final int TYPE_HIGH_MULTIPLIER = 2;
    private static int[] types = new int[]{
            TYPE_INSTANT_GAIN,
            TYPE_LIGHT_MULTIPLIER,
            TYPE_HIGH_MULTIPLIER
    };

    private static final double CHANCE_INSTANT_GAIN = 0.5;
    private static final double CHANCE_LIGHT_MULTIPLIER = 0.4;
    private static final double CHANCE_HIGH_MULTIPLIER = 0.1;
    private static double[] chances = new double[]{
            CHANCE_INSTANT_GAIN,
            CHANCE_LIGHT_MULTIPLIER,
            CHANCE_HIGH_MULTIPLIER
    };

    public int type;
    int timeToDespawn;
    int timeToSpawn;
    private Clicker clicker;

    Golden(Clicker clicker){
        this.type = randomType();
        this.timeToSpawn = randomTimeToSpawn(clicker);
        this.timeToDespawn = getTimeToDespawn(clicker);
        this.clicker = clicker;
    }

    private static int randomType(){
        double chance = 0;
        double rand = new Random().nextDouble();
        for(int i=0; i<chances.length; i++){
            chance += chances[i];
            if(chance > rand)
                return types[i];
        }
        return TYPE_INSTANT_GAIN;
    }

    private static int randomTimeToSpawn(Clicker clicker){
        // min and max time
        double spawnMultiplier = 1;
        for(int i=0; i<clicker.getUpgradesGolden(); i++)
            spawnMultiplier *= ClickerStatics.goldenUpgradeSpawnTimeMultiplier[i];
        double minTime = ClickerStatics.goldenCrepeMinTime * spawnMultiplier;
        double maxTime = ClickerStatics.goldenCrepeMaxTime * spawnMultiplier;

        double center = maxTime - minTime;
        double standard = center/6;

        double time = new Random().nextGaussian() * standard + center;
        if(time < minTime)
            time = minTime;
        else if(time > maxTime)
            time = maxTime;
        return (int) time;
    }

    private static int getTimeToDespawn(Clicker clicker){
        double despawnMultiplier = 1;
        for(int i=0; i<clicker.getUpgradesGolden(); i++)
            despawnMultiplier *= ClickerStatics.goldenUpgradeDespawnTimeMultiplier[i];
        return (int) (ClickerStatics.goldenCrepeDespawnTime * despawnMultiplier);
    }

    public int getDuration(){
        double durationMultiplier = 1;
        for(int i=0; i<clicker.getUpgradesGolden(); i++)
            durationMultiplier *= ClickerStatics.goldenUpgradeDurationMultiplier[i];
        switch (this.type){
            case TYPE_INSTANT_GAIN:
                return 0;
            case TYPE_LIGHT_MULTIPLIER:
                return (int) (ClickerStatics.goldenCrepeLightDuration * durationMultiplier);
            case TYPE_HIGH_MULTIPLIER:
                return (int) (ClickerStatics.goldenCrepeHighDuration * durationMultiplier);
        }
        return 0;
    }
}
