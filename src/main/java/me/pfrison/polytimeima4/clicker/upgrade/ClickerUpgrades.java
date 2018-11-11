package me.pfrison.polytimeima4.clicker.upgrade;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

import me.pfrison.polytimeima4.clicker.Clicker;
import me.pfrison.polytimeima4.clicker.ClickerMechanics;
import me.pfrison.polytimeima4.graphics.ClickerGraphics;

public class ClickerUpgrades {
    private Context context;
    private Clicker clicker;

    private LinearLayout upgradeRootLayout;
    private ArrayList<UpgradeStatus> upgradesStatuses;
    private Upgrade[] upgrades;

    public ClickerUpgrades(Context context, Clicker clicker, LinearLayout upgradeRootLayout){
        this.context = context;
        this.clicker = clicker;

        // build upgrades
        this.upgradesStatuses = new ArrayList<>();
        this.upgrades = ClickerMechanics.getAvailableUpgrades(clicker);
        for (Upgrade upgrade : upgrades)
            upgradesStatuses.add(createUpgradeStatus(upgrade));


        // add upgrades in order
        this.upgradeRootLayout = upgradeRootLayout;
        upgradesStatuses = UpgradeStatus.sortStatuses(upgradesStatuses);
        for(UpgradeStatus upgradeStatus : upgradesStatuses)
            upgradeRootLayout.addView(upgradeStatus.item);

    }

    private UpgradeStatus createUpgradeStatus(Upgrade upgrade){
        // create item
        ClickerUpgradeItem item = new ClickerUpgradeItem(upgrade, context);

        // can buy
        boolean canBuy = ClickerMechanics.canBuyUpgrade(upgrade, clicker);
        if(!canBuy)
            ClickerGraphics.darkenLayout(item, false);

        // upgrade status
        UpgradeStatus upgradeStatus = new UpgradeStatus(upgrade, canBuy, item);

        // click listener
        item.setOnClickListener(getUpgradeOnClickListener(upgradeStatus));

        return upgradeStatus;
    }

    private View.OnClickListener getUpgradeOnClickListener(final UpgradeStatus upgradeStatus){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!upgradeStatus.canBuy)
                    return;

                // subtract money
                ClickerMechanics.buyUpgrade(clicker, upgradeStatus.upgrade);

                // update root list
                upgradeRootLayout.removeViewAt(upgradeStatus.position);

                // update status
                upgradesStatuses = UpgradeStatus.removeStatus(upgradesStatuses, upgradeStatus);

                // remove upgrade from list
                ArrayList<Upgrade> upgradesArray = new ArrayList<>(Arrays.asList(upgrades));
                for(int i=0; i<upgradesArray.size(); i++){
                    if(upgradesArray.get(i).equals(upgradeStatus.upgrade)){
                        upgradesArray.remove(i);
                        break;
                    }
                }
                upgrades = upgradesArray.toArray(new Upgrade[upgradesArray.size()]);

                // update
                recheckAllCanBuy();
            }
        };
    }
    public void recheckAllCanBuy(){
        // check for current upgrades
        for(int i=0; i<upgradesStatuses.size(); i++){
            boolean canBuy = ClickerMechanics.canBuyUpgrade(upgradesStatuses.get(i).upgrade, clicker);
            if (!canBuy && upgradesStatuses.get(i).canBuy) // can buy -> can't buy
                ClickerGraphics.darkenLayout(upgradesStatuses.get(i).item, false);
            if (canBuy && !upgradesStatuses.get(i).canBuy) // can't buy -> can buy
                ClickerGraphics.lightenLayout(upgradesStatuses.get(i).item, false);
            upgradesStatuses.get(i).canBuy = canBuy;
        }

        // new upgrades ?
        Upgrade[] newUpgrades = ClickerMechanics.getAvailableUpgrades(clicker);
        if(newUpgrades.length > upgrades.length){
            int offset = 0;
            for(int i=0; i<newUpgrades.length; i++){
                if(i - offset >= upgrades.length
                        || !upgrades[i-offset].equals(newUpgrades[i])){
                    // build the update
                    upgradesStatuses.add(createUpgradeStatus(newUpgrades[i]));
                    offset++;
                }
            }
            // add upgrades in order
            upgradesStatuses = UpgradeStatus.sortStatuses(upgradesStatuses);
            upgradeRootLayout.removeAllViews();
            for(UpgradeStatus upgradeStatus : upgradesStatuses)
                upgradeRootLayout.addView(upgradeStatus.item);

            this.upgrades = newUpgrades;
        }
    }

    private static class UpgradeStatus {
        private boolean canBuy;
        private ClickerUpgradeItem item;
        private Upgrade upgrade;
        private double cost;
        private int position = -1;

        private UpgradeStatus(Upgrade upgrade, boolean canBuy, ClickerUpgradeItem item){
            this.upgrade = upgrade;
            this.canBuy = canBuy;
            this.item = item;

            this.cost = ClickerMechanics.getUpgradePrize(upgrade);
        }

        private static ArrayList<UpgradeStatus> removeStatus(ArrayList<UpgradeStatus> upgradesStatuses, UpgradeStatus toRemove){
            int intToRemove = toRemove.position;
            for(int i=0; i<upgradesStatuses.size(); i++) {
                if (upgradesStatuses.get(i).position == intToRemove) {
                    upgradesStatuses.remove(i);
                    break;
                }
            }
            for(int i=0; i<upgradesStatuses.size(); i++)
                if(upgradesStatuses.get(i).position > intToRemove)
                    upgradesStatuses.get(i).position--;
            return upgradesStatuses;
        }

        private static ArrayList<UpgradeStatus> sortStatuses(ArrayList<UpgradeStatus> upgradeStatuses){
            for(int i=0; i<upgradeStatuses.size(); i++){
                for(int j=i + 1; j<upgradeStatuses.size(); j++){
                    if(upgradeStatuses.get(i).cost > upgradeStatuses.get(j).cost){
                        UpgradeStatus temp = upgradeStatuses.get(j);
                        upgradeStatuses.set(j, upgradeStatuses.get(i));
                        upgradeStatuses.set(i, temp);
                    }
                }
            }
            for(int i=0; i<upgradeStatuses.size(); i++)
                upgradeStatuses.get(i).position = i;
            return upgradeStatuses;
        }
    }
}