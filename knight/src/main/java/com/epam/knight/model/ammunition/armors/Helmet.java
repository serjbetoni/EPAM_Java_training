package com.epam.knight.model.ammunition.armors;

public class Helmet implements Armor {
    private int protection;
    private int weight;
    private int cost;

    public Helmet(int protection, int weight, int cost) {
        this.protection = protection;
        this.weight = weight;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Helmet{protection=" + protection + ", weight=" + weight + ", cost=" + cost + "}";
    }

    public int getProtection() {
        return protection;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
