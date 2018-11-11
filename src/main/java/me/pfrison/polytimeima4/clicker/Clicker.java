package me.pfrison.polytimeima4.clicker;

import java.util.ArrayList;

import me.pfrison.polytimeima4.clicker.golden.ClickerGoldenEffect;

public class Clicker {
    public ArrayList<ClickerGoldenEffect> clickerEffects;

    private double crepes;
    private double crepesPerSecond = 0;
    private double crepesPerClick = 1;
    private int[] flavors;
    private int[] upgradesFlavors;
    private boolean[] upgradesSupplements;
    private int upgradesGloves;
    private int upgradesGolden;

    private double handMadeCrepes;
    private double madeCrepes;
    private double maxCrepes;
    private double hoursPlayed;
    private double goldenClicked;

    public int level;

    public Clicker(){
        this(0, new int[0], new int[ClickerStatics.INGREDIENTS_COUNT], new boolean[ClickerStatics.SUPPLEMENTS_COUNT], 0, 0,
                0, 0, 0, 0, 0);
    }
    public Clicker(double crepes, int[] flavors, int[] upgradesFlavors, boolean[] upgradesSupplements, int upgradesGloves, int upgradesGolden,
                   double handMadeCrepes, double madeCrepes, double maxCrepes, double hoursPlayed, double goldenClicked){
        this.crepes = crepes;
        this.flavors = flavors;
        this.upgradesFlavors = upgradesFlavors;
        this.upgradesSupplements = upgradesSupplements;
        this.upgradesGloves = upgradesGloves;
        this.upgradesGolden = upgradesGolden;

        this.handMadeCrepes = handMadeCrepes;
        this.madeCrepes = madeCrepes;
        this.maxCrepes = maxCrepes;
        this.hoursPlayed = hoursPlayed;
        this.goldenClicked = goldenClicked;

        this.clickerEffects = new ArrayList<>();

        this.level = 0;
        for (int flavor : flavors) {
            if (flavor != 0)
                this.level++;
            else
                break;
        }
        updateCrepesPerSeconds();
        updateCrepesPerClick();
    }

    public double getCrepes(){return this.crepes;}
    void addCrepes(double crepes){
        this.crepes += crepes;
        if(crepes > 0)
            this.madeCrepes += crepes;
        if(crepes > this.maxCrepes)
            this.maxCrepes = crepes;
    }
    void addHandMadeCrepes(double crepes){
        addCrepes(crepes);
        this.handMadeCrepes = this.handMadeCrepes + crepes;
    }
    public double getHandMadeCrepes(){return this.handMadeCrepes;}
    public double getMadeCrepes(){return this.madeCrepes;}
    double getMaxCrepes(){return maxCrepes;}
    public double getGoldenClicked(){return goldenClicked;}
    void addGoldenClick(){this.goldenClicked++;}

    public void addTimePlayed(int millis){this.hoursPlayed += (double) millis / 3600000d;}
    public int getHoursPlayed(){return (int) hoursPlayed;}

    public int getFlavorAt(int index){
        if(index < 0 || index >= this.flavors.length)
            return 0;
        return this.flavors[index];
    }
    void addOneFlavor(int index){
        this.flavors[index]++;
        updateCrepesPerSeconds();
        updateCrepesPerClick();
    }
    void addNextFlavor(){
        if(flavors.length >= ClickerStatics.INGREDIENTS_COUNT)
            return;
        int[] newFlavors = new int[flavors.length + 1];
        System.arraycopy(flavors, 0, newFlavors, 0, flavors.length);
        newFlavors[flavors.length] = 1;
        flavors = newFlavors;
        level++;
        updateCrepesPerSeconds();
        updateCrepesPerClick();
    }

    public int getUpgradesFlavorsAt(int index){
        if(index < 0 || index >= this.upgradesFlavors.length)
            return 0;
        return upgradesFlavors[index];
    }
    void addOneUpgradeFlavor(int index){
        this.upgradesFlavors[index]++;
        updateCrepesPerSeconds();
        updateCrepesPerClick();
    }
    public boolean getUpgradesSupplementsAt(int index){
        if(index < 0 || index >= this.upgradesSupplements.length)
            return false;
        return upgradesSupplements[index];
    }
    void addOneUpgradesSupplements(int index){
        this.upgradesSupplements[index] = true;
        updateCrepesPerSeconds();
        updateCrepesPerClick();
    }
    public int getUpgradesGloves(){return upgradesGloves;}
    void addOneUpgradesGlove(){
        this.upgradesGloves++;
        updateCrepesPerSeconds();
        updateCrepesPerClick();
    }
    public int getUpgradesGolden(){return upgradesGolden;}
    void addOneUpgradesGolden(){this.upgradesGolden++;}

    public void updateCrepesPerSeconds(){
        crepesPerSecond = 0;

        // FLAVORS
        // butter = n * (money/s + SOM(k=4 -> upgrades)(butterAddition_k * nOthers)) * 2^(upgrades > 3 ? 3 : upgrades)
        double butterAddition = 0;
        double upgrades;
        if(upgradesFlavors[0] > 3){
            upgrades = 3;
            for(int i=4; i<upgradesFlavors[0]; i++)
                for(int j=1; j<flavors.length; j++)
                    butterAddition += (double) flavors[j] * ClickerStatics.butterAddition[i];
        }else
            upgrades = upgradesFlavors[0];
        if(flavors.length > 0)
            crepesPerSecond += (double) flavors[0] * (ClickerStatics.ingredientsBaseMoneyPerSecond[0] + butterAddition) * Math.pow(2, upgrades);

        // others = n * money/s * 2^upgrades
        for(int i=1; i<flavors.length; i++)
            crepesPerSecond += (double) flavors[i] * ClickerStatics.ingredientsBaseMoneyPerSecond[i] * Math.pow(2, upgradesFlavors[i]);

        // SUPPLEMENTS
        for(int i=0; i<upgradesSupplements.length; i++)
            if(upgradesSupplements[i])
                crepesPerSecond *= ClickerStatics.supplementsMultiplications[i];

        // EFFECTS
        for (ClickerGoldenEffect effect : clickerEffects)
            if (effect.type == ClickerGoldenEffect.TYPE_CREPE_PER_SECONDS)
                crepesPerSecond *= effect.multiplier;
    }
    public double getCrepesPerSeconds(){return crepesPerSecond;}

    public void updateCrepesPerClick(){
        crepesPerClick = 1;

        // butter upgrade
        crepesPerClick *= Math.pow(2, (upgradesFlavors[0] > 3 ? 3 : upgradesFlavors[0]));
        for(int i=4; i<upgradesFlavors[0]; i++)
            crepesPerClick += ClickerStatics.butterAddition[i];

        // glove upgrade
        crepesPerClick += crepesPerSecond * ClickerStatics.glovesMultiplication * upgradesGloves;

        // EFFECTS
        for (ClickerGoldenEffect effect : clickerEffects)
            if (effect.type == ClickerGoldenEffect.TYPE_CREPE_PER_CLICKS)
                crepesPerClick *= effect.multiplier;
    }
    double getCrepesPerClick(){return crepesPerClick;}

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder(String.valueOf(crepes)).append("\n");
        str.append("-----\n");
        for (int flavor : flavors) str.append(String.valueOf(flavor)).append("\n");
        str.append("-----\n");
        for (int upgrade : upgradesFlavors) str.append(String.valueOf(upgrade)).append("\n");
        str.append("-----\n");
        for (boolean upgrade : upgradesSupplements) str.append(String.valueOf(upgrade ? 1 : 0)).append("\n");
        str.append("-----\n");
        str.append(String.valueOf(upgradesGloves)).append("\n");
        str.append("-----\n");
        str.append(String.valueOf(upgradesGolden)).append("\n");
        str.append("-----\n");
        str.append(String.valueOf(handMadeCrepes)).append("\n");
        str.append("-----\n");
        str.append(String.valueOf(madeCrepes)).append("\n");
        str.append("-----\n");
        str.append(String.valueOf(maxCrepes)).append("\n");
        str.append("-----\n");
        str.append(String.valueOf(hoursPlayed)).append("\n");
        str.append("-----\n");
        str.append(String.valueOf(goldenClicked)).append("\n");
        return str.toString();
    }
}
