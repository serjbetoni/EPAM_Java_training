package com.epam.knight.model;

import com.epam.knight.model.ammunition.Ammunition;

import java.util.Arrays;

/**
 * Stores equipped ammunition and calculated stats.
 */
public class Knight {

    private Ammunition[] ammunition = new Ammunition[0];

    public Ammunition[] getAmmunition() {
        return ammunition;
    }

    /**
     * Add new ammunition element to knight
     *
     * @param element that should be equipped to the knight
     */

    public void equip(Ammunition element) {
        ammunition = Arrays.copyOf(ammunition, ammunition.length + 1);
        ammunition[ammunition.length - 1] = element;
    }

}
