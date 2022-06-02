package com.epam.knight.model.comparator;

import com.epam.knight.model.ammunition.Ammunition;

import java.io.Serializable;
import java.util.Comparator;

public class ComparatorOfCost implements Comparator<Ammunition>, Serializable {
    private static final long serialVersionUID = 1;

    @Override
    public int compare(Ammunition firstItem, Ammunition secondItem) {
        return Integer.compare(firstItem.getCost(), secondItem.getCost());
    }
}
