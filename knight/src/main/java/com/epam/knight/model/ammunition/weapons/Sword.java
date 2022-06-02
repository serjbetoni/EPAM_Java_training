package com.epam.knight.model.ammunition.weapons;

public class Sword implements Weapon {
    private int damage;
    private int weight;
    private int cost;

    public Sword(int damage, int weight, int cost) {
        this.damage = damage;
        this.weight = weight;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Sword{damage=" + damage + ", weight=" + weight + ", cost=" + cost + "}";
    }

    public int getDamage() {
        return damage;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
