package com.epam.knight.model;

import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.armors.Armor;
import com.epam.knight.model.ammunition.weapons.Weapon;
import com.epam.knight.model.comparator.ComparatorOfCost;
import com.epam.knight.model.comparator.ComparatorOfWeight;

import java.util.Arrays;

/**
 * Manipulates with knight's ammunition and updates knight stats.
 */
public class KnightAmmunitionManager {
    private Knight knight;

    public KnightAmmunitionManager(Knight knight) {
        this.knight = knight;
    }

    /**
     * Equips item to knight and update knight's stats
     */
    public void equipAmmunitionToKnight(Ammunition item) {
        knight.equip(item);
    }

    public Ammunition[] getAmmunition() {
        return knight.getAmmunition();
    }

    public int calculateAmmunitionWeight() {
        int sumAmmunitionWeight = 0;
        for (Ammunition elementOfAmmunition : knight.getAmmunition()) {
            sumAmmunitionWeight += elementOfAmmunition.getWeight();
        }
        return sumAmmunitionWeight;
    }

    public int calculateAmmunitionCost() {
        int sumAmmunitionCost = 0;
        for (Ammunition elementOfAmmunition : knight.getAmmunition()) {
            sumAmmunitionCost += elementOfAmmunition.getCost();
        }
        return sumAmmunitionCost;
    }

    public int calculateAmmunitionDamage() {
        int sumDamage = 0;
        for (int i = 0; i < knight.getAmmunition().length; i++) {
            if (knight.getAmmunition()[i] instanceof Weapon) {
                sumDamage += ((Weapon) knight.getAmmunition()[i]).getDamage();
            }
        }
        return sumDamage;
    }

    public int calculateAmmunitionProtection() {
        int sumProtection = 0;
        for (int i = 0; i < knight.getAmmunition().length; i++) {
            if (knight.getAmmunition()[i] instanceof Armor) {
                sumProtection += ((Armor) knight.getAmmunition()[i]).getProtection();
            }
        }
        return sumProtection;
    }

    public void sortByCost(Ammunition[] ammunition) {
        Arrays.sort(knight.getAmmunition(), new ComparatorOfCost());
    }

    public void sortByWeight(Ammunition[] ammunition) {
        Arrays.sort(ammunition, new ComparatorOfWeight());
    }

    public Ammunition[] searchByCost(int minCost, int maxCost) {
        Ammunition[] ammunitionSuitableForThePrice = new Ammunition[0];
        for (Ammunition cost : knight.getAmmunition()) {
            if (cost.getCost() >= minCost && cost.getCost() <= maxCost) {
                ammunitionSuitableForThePrice = Arrays.copyOf(ammunitionSuitableForThePrice,
                        ammunitionSuitableForThePrice.length + 1);
                ammunitionSuitableForThePrice[ammunitionSuitableForThePrice.length - 1] = cost;
            }
        }
        return ammunitionSuitableForThePrice;
    }

    public Ammunition[] searchByWeight(int minWeight, int maxWeight) {
        Ammunition[] ammunitionSuitableForTheWeight = new Ammunition[0];
        for (Ammunition weight : knight.getAmmunition()) {
            if (weight.getWeight() >= minWeight && weight.getWeight() <= maxWeight) {
                ammunitionSuitableForTheWeight = Arrays.copyOf(ammunitionSuitableForTheWeight,
                        ammunitionSuitableForTheWeight.length + 1);
                ammunitionSuitableForTheWeight[ammunitionSuitableForTheWeight.length - 1] = weight;
            }
        }
        return ammunitionSuitableForTheWeight;
    }
}
