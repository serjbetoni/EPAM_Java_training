package com.epam.knight.controller;

import com.epam.knight.model.Knight;
import com.epam.knight.model.KnightAmmunitionManager;
import com.epam.knight.model.ammunition.Ammunition;

/**
 * Generates knight with some ammunition.
 */
public final class KnightGenerator {

    /**
     * Use it to quickly generate knight
     *
     * @return knight
     */
    private KnightGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static Knight generateKnight() {
        Knight knight = new Knight();
        KnightAmmunitionManager manager = new KnightAmmunitionManager(knight);
        Ammunition helmet = AmmunitionGenerator.generateArmour();
        Ammunition sword = AmmunitionGenerator.generateWeapon();
        manager.equipAmmunitionToKnight(helmet);
        manager.equipAmmunitionToKnight(sword);
        return knight;
    }


}
