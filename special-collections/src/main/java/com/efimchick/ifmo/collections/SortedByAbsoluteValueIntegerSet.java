package com.efimchick.ifmo.collections;

import java.util.Comparator;
import java.util.TreeSet;

class SortedByAbsoluteValueIntegerSet extends TreeSet<Integer> {
    private static final long serialVersionUID = 1;

    private static Comparator<Integer> sortValue = Comparator.comparingInt(Math::abs);

    public SortedByAbsoluteValueIntegerSet() {
        super(sortValue);
    }
}
