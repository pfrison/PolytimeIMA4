package me.pfrison.polytimeima4.clicker;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

import me.pfrison.polytimeima4.clicker.golden.ClickerGoldenEffect;
import me.pfrison.polytimeima4.clicker.golden.Golden;
import me.pfrison.polytimeima4.clicker.upgrade.Upgrade;

public class ClickerMechanics {

    static double getIngredientPrize(int level, int count){
        return ClickerStatics.ingredientsBaseCost[level] * Math.pow(1.15, count);
    }

    static boolean canBuyIngredient(int level, Clicker clicker){
        return getIngredientPrize(level, clicker.getFlavorAt(level)) <= clicker.getCrepes();
    }

    static void buyIngredient(Clicker clicker, int level){
        clicker.addCrepes(-1 * getIngredientPrize(level, clicker.getFlavorAt(level)));

        if (clicker.level != level) {
            clicker.addOneFlavor(level);
        }else{
            clicker.addNextFlavor();
        }
    }

    public static double getUpgradePrize(Upgrade upgrade){
        switch (upgrade.type){
            case Upgrade.INGREDIENT:
                double[] ingredient;
                if(upgrade.identifier[0] >= ClickerStatics.ingredientsUpgradesCost.length)
                    ingredient = ClickerStatics.ingredientsUpgradesCost[ClickerStatics.ingredientsUpgradesCost.length - 1];
                else if(upgrade.identifier[0] < 0)
                    ingredient = ClickerStatics.ingredientsUpgradesCost[0];
                else
                    ingredient = ClickerStatics.ingredientsUpgradesCost[upgrade.identifier[0]];

                if(upgrade.identifier[1] >= ingredient.length)
                    return ingredient[ingredient.length - 1];
                if(upgrade.identifier[1] < 0)
                    return ingredient[0];
                return ingredient[upgrade.identifier[1]];
            case Upgrade.SUPPLEMENT:
                if(upgrade.identifier[0] >= ClickerStatics.supplementsCost.length)
                    return ClickerStatics.supplementsCost[ClickerStatics.supplementsCost.length - 1];
                if(upgrade.identifier[0] < 0)
                    return ClickerStatics.supplementsCost[0];
                return ClickerStatics.supplementsCost[upgrade.identifier[0]];
            case Upgrade.GLOVE:
                if(upgrade.identifier[0] >= ClickerStatics.glovesCost.length)
                    return ClickerStatics.glovesCost[ClickerStatics.glovesCost.length - 1];
                if(upgrade.identifier[0] < 0)
                    return ClickerStatics.glovesCost[0];
                return ClickerStatics.glovesCost[upgrade.identifier[0]];
            case Upgrade.GOLDEN:
                if(upgrade.identifier[0] >= ClickerStatics.goldenUpgradeCost.length)
                    return ClickerStatics.goldenUpgradeCost[ClickerStatics.goldenUpgradeCost.length - 1];
                if(upgrade.identifier[0] < 0)
                    return ClickerStatics.goldenUpgradeCost[0];
                return ClickerStatics.goldenUpgradeCost[upgrade.identifier[0]];
        }
        return Double.MAX_VALUE;
    }

    public static Upgrade[] getAvailableUpgrades(Clicker clicker){
        ArrayList<Upgrade> upgrades = new ArrayList<>();

        // ingredients
        for(int i=0; i<ClickerStatics.ingredientsUpgradesNeeds.length; i++) {
            int nextLevel = clicker.getUpgradesFlavorsAt(i);

            // no more upgrades ?
            if (nextLevel >= ClickerStatics.ingredientsUpgradesNeeds[i].length)
                continue;

            for (int j = nextLevel; j<ClickerStatics.ingredientsUpgradesNeeds[i].length; j++) {
                // conditions met ?
                if (ClickerStatics.ingredientsUpgradesNeeds[i][j] <= clicker.getFlavorAt(i))
                    upgrades.add(new Upgrade(Upgrade.INGREDIENT, i, j));
            }
        }

        // supplements
        for(int i=0; i<ClickerStatics.supplementsNeeds.length; i++){
            if(!clicker.getUpgradesSupplementsAt(i)
                    && ClickerStatics.supplementsNeeds[i] <= clicker.getMaxCrepes())
                upgrades.add(new Upgrade(Upgrade.SUPPLEMENT, i));
        }

        // gloves
        int nextLevel = clicker.getUpgradesGloves();
        // no more upgrades ?
        if(nextLevel < ClickerStatics.glovesNeeds.length){
            // conditions met ?
            if(clicker.getHandMadeCrepes() >= ClickerStatics.glovesNeeds[nextLevel])
                upgrades.add(new Upgrade(Upgrade.GLOVE, nextLevel));
        }

        // golden
        nextLevel = clicker.getUpgradesGolden();
        // no more upgrades ?
        if(nextLevel < ClickerStatics.goldenUpgradeNeeds.length){
            // conditions met ?
            if(clicker.getGoldenClicked() >= ClickerStatics.goldenUpgradeNeeds[nextLevel])
                upgrades.add(new Upgrade(Upgrade.GOLDEN, nextLevel));
        }

        return upgrades.toArray(new Upgrade[upgrades.size()]);
    }

    public static boolean canBuyUpgrade(Upgrade upgrade, Clicker clicker){
        return getUpgradePrize(upgrade) <= clicker.getCrepes();
    }

    public static void buyUpgrade(Clicker clicker, Upgrade upgrade){
        clicker.addCrepes(-1 * getUpgradePrize(upgrade));

        switch (upgrade.type){
            case Upgrade.INGREDIENT:
                clicker.addOneUpgradeFlavor(upgrade.identifier[0]);
                break;
            case Upgrade.SUPPLEMENT:
                clicker.addOneUpgradesSupplements(upgrade.identifier[0]);
                break;
            case Upgrade.GLOVE:
                clicker.addOneUpgradesGlove();
                break;
            case Upgrade.GOLDEN:
                clicker.addOneUpgradesGolden();
                break;
        }
    }

    public static void addClick(Clicker clicker){
        clicker.addHandMadeCrepes(clicker.getCrepesPerClick());
    }

    public static void addWait(Clicker clicker, int millis){
        double seconds = (double) millis / 1000d;
        double moneyPerSecond = clicker.getCrepesPerSeconds();
        clicker.addCrepes(moneyPerSecond * seconds);
    }

    public static double clickGolden(Golden golden, Clicker clicker, ViewGroup goldenEffectSides,
                                     ImageView clickerLight1, ImageView clickerLight2){
        clicker.addGoldenClick();
        int duration;
        switch (golden.type){
            case Golden.TYPE_INSTANT_GAIN:
                double gain1 = (ClickerStatics.goldenCrepeInstantGainCrepeMultiplier * clicker.getCrepes()) + ClickerStatics.goldenCrepeInstantGainAddition;
                double gain2 = (ClickerStatics.goldenCrepeInstantGainCrepePerSecMultiplier * clicker.getCrepesPerSeconds()) + ClickerStatics.goldenCrepeInstantGainAddition;
                if(gain1 < gain2) {
                    clicker.addCrepes(gain1);
                    return gain1;
                }else{
                    clicker.addCrepes(gain2);
                    return gain2;
                }
            case Golden.TYPE_LIGHT_MULTIPLIER:
                duration = golden.getDuration();
                new ClickerGoldenEffect(duration, ClickerStatics.goldenCrepeLightMultiplier, ClickerGoldenEffect.TYPE_CREPE_PER_SECONDS, clicker,
                        goldenEffectSides, clickerLight1, clickerLight2);
                return duration;
            case Golden.TYPE_HIGH_MULTIPLIER:
                duration = golden.getDuration();
                new ClickerGoldenEffect(duration, ClickerStatics.goldenCrepeHighMultiplier, ClickerGoldenEffect.TYPE_CREPE_PER_CLICKS, clicker,
                        goldenEffectSides, clickerLight1, clickerLight2);
                return duration;
        }
        return 0;
    }
}
