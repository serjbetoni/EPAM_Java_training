package com.efimchick.ifmo.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class MedianQueue extends LinkedList<Integer> {
    private static final long serialVersionUID = 1;
    public static final int CHECK_EVEN_TWO = 2;
    public static final int HALF_DIVISOR = 2;


    @Override
    public Integer peek() {
        List<Integer> list = new ArrayList<>(this);
        Collections.sort(list);
        int index = this.size() / HALF_DIVISOR;
        if (this.size() % CHECK_EVEN_TWO == 0) {
            index--;
        }
        return list.get(index);
    }

    @Override
    public Integer poll() {
        List<Integer> list = new ArrayList<>(this);
        Collections.sort(list);
        int index = this.size() / HALF_DIVISOR;
        if (this.size() % CHECK_EVEN_TWO == 0) {
            index--;
        }
        Integer median = list.get(index);
        this.remove(median);
        return median;
    }
}
