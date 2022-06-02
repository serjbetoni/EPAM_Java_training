package com.efimchick.ifmo.collections;

import java.util.ArrayList;
import java.util.Collection;

class PairStringList extends ArrayList<String> {
    private static final long serialVersionUID = 1;
    public static final int CHECK_EVEN_TWO = 2;
    public static final int INDEX_DOUBLE_STEP = 2;

    @Override
    public boolean add(String str) {
        super.add(str);
        return super.add(str);
    }

    @Override
    public void add(int index, String element) {
        if (index % CHECK_EVEN_TWO == 0) {
            super.add(index, element);
            super.add(index, element);
        } else {
            super.add(index + 1, element);
            super.add(index + 1, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        boolean isAdd;
        for (String str : c) {
            isAdd = add(str);
            if (!isAdd) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        if (index % CHECK_EVEN_TWO == 0) {
            for (String str : c) {
                add(index, str);
                index += INDEX_DOUBLE_STEP;
            }
        } else {
            for (String str : c) {
                add(index, str);
                index++;
                index++;
            }
        }
        return true;
    }

    @Override
    public boolean remove(Object str) {
        super.remove(str);
        return super.remove(str);
    }

    @Override
    public String remove(int index) {
        if (index % CHECK_EVEN_TWO == 0) {
            super.remove(index);
            return super.remove(index + 1);
        } else {
            super.remove(index);
            return super.remove(index - 1);
        }
    }

    @Override
    public String set(int index, String element) {
        if (index % CHECK_EVEN_TWO == 0) {
            super.set(index, element);
            return super.set(index + 1, element);
        } else {
            super.set(index, element);
            return super.set(index - 1, element);
        }
    }
}
