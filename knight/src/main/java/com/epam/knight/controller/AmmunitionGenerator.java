package com.epam.knight.controller;

import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.armors.Helmet;
import com.epam.knight.model.ammunition.weapons.Sword;

/**
 * Generates pieces of {@link Ammunition}.
 */
public final class AmmunitionGenerator {
    private static final int START_STATS_TEN = 10;
    private static final int START_STATS_TWENTY = 20;
    private static final int START_STATS_THIRTY = 30;

    private AmmunitionGenerator() {
        throw new IllegalStateException("Utility class");
    }

    static Ammunition generateArmour() {
        return new Helmet(START_STATS_TEN, START_STATS_TWENTY, START_STATS_THIRTY);
    }

    static Ammunition generateWeapon() {
        return new Sword(START_STATS_THIRTY, START_STATS_TEN, START_STATS_TWENTY);
    }
}
