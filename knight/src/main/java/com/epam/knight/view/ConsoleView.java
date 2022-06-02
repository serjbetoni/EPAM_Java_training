package com.epam.knight.view;

import com.epam.knight.controller.KnightController;
import com.epam.knight.model.KnightAmmunitionManager;
import com.epam.knight.model.ammunition.Ammunition;
import com.epam.knight.model.ammunition.armors.Helmet;
import com.epam.knight.model.ammunition.weapons.Sword;
import com.epam.knight.view.enums.TypeOfAction;
import com.epam.knight.model.ammunition.AmmunitionType;
import com.epam.knight.view.enums.TypeOfSort;

/**
 * Implements all application input and output.
 */
public class ConsoleView {
    private static final String MAIN_MENU = "Main menu:\n" +
            "1. Print knight status\n" +
            "2. Show ammunition\n" +
            "3. Equip ammunition\n" +
            "4. Sort ammunition\n" +
            "5. Search ammunition\n" +
            "6. Exit";
    private static final String CHOSE_OPTION = "Choose option:";
    private static final String WHAT_AMMUNITION_TO_WEAR = "What ammunition do you want to wear? \n " +
            "1. Sword \n " +
            "2. Helmet";
    private static final String SORT_AMMUNITION = "Choose sort type:\n 1. Cost\n 2. Weight";
    private static final String SEARCH_AMMUNITION = "Choose search field:\n 1. Cost\n 2. Weight";
    private static final String AMMUNITION_NOT_FOUND = "Ammunition not found!";
    private static final String END_MESSAGE = "Bue!";
    private static final String INPUT_SWORD = "Input sword ";
    private static final String INPUT_HELMET = "Input helmet ";
    private static final String INPUT_MINIMUM = "Input minimum ";
    private static final String INPUT_MAXIMUM = "Input maximum ";
    private static final String AMMUNITION = "Ammunition ";
    private static final String COST = "cost: ";
    private static final String DAMAGE = "damage: ";
    private static final String WEIGHT = "weight: ";
    private static final String PROTECTION = "protection: ";
    private static final int RANGE_ONE = 1;
    private static final int RANGE_TWO = 2;
    private static final int RANGE_SIX = 6;

    public void showMainMenu(KnightAmmunitionManager knight, KnightController knightController) {
        TypeOfAction typeOfAction;
        do {
            System.out.println(MAIN_MENU);
            System.out.println(CHOSE_OPTION);
            int numberOfTask = knightController.getNumberFromUser(RANGE_ONE, RANGE_SIX);
            typeOfAction = TypeOfAction.values()[numberOfTask - 1];
            switch (typeOfAction) {
                case PRINT_KNIGHT_STATS:
                    printKnightStatus(knight);
                    break;
                case SHOW_AMMUNITION:
                    showAmmunitionToScreen(knight.getAmmunition());
                    break;
                case EQUIP_AMMUNITION:
                    equipAmmunition(knight, knightController);
                    break;
                case SORT_AMMUNITION:
                    sortAmmunition(knight, knightController);
                    break;
                case SEARCH_AMMUNITION:
                    searchAmmunition(knight, knightController);
                    break;
                case EXIT:
                    System.out.println(END_MESSAGE);
                    break;
            }
        } while (typeOfAction != TypeOfAction.EXIT);


    }

    private void printKnightStatus(KnightAmmunitionManager knight) {
        System.out.println(AMMUNITION + COST + knight.calculateAmmunitionCost());
        System.out.println(AMMUNITION + WEIGHT + knight.calculateAmmunitionWeight());
        System.out.println(AMMUNITION + DAMAGE + knight.calculateAmmunitionDamage());
        System.out.println(AMMUNITION + PROTECTION + knight.calculateAmmunitionProtection());
    }

    private void equipAmmunition(KnightAmmunitionManager knight, KnightController knightController) {
        System.out.println(WHAT_AMMUNITION_TO_WEAR);
        System.out.println(CHOSE_OPTION);
        int numberOfAmmunition = knightController.getNumberFromUser(RANGE_ONE, RANGE_TWO);
        AmmunitionType ammunitionType = AmmunitionType.values()[numberOfAmmunition - 1];
        if (ammunitionType == AmmunitionType.SWORD) {
            knight.equipAmmunitionToKnight(addSword(knightController));
        } else if (ammunitionType == AmmunitionType.HELMET) {
            knight.equipAmmunitionToKnight(addHelmet(knightController));
        } else {
            System.out.println(AMMUNITION_NOT_FOUND);
        }
    }

    private void sortAmmunition(KnightAmmunitionManager knight, KnightController knightController) {
        System.out.println(SORT_AMMUNITION);
        System.out.println(CHOSE_OPTION);
        int numberOfSort = knightController.getNumberFromUser(RANGE_ONE, RANGE_TWO);
        TypeOfSort typeOfSort = TypeOfSort.values()[numberOfSort - 1];
        if (typeOfSort == TypeOfSort.COST) {
            knight.sortByCost(knight.getAmmunition());
            showAmmunitionToScreen(knight.getAmmunition());
        } else if (typeOfSort == TypeOfSort.WEIGHT) {
            knight.sortByWeight(knight.getAmmunition());
            showAmmunitionToScreen(knight.getAmmunition());
        } else {
            System.out.println(AMMUNITION_NOT_FOUND);
        }
    }

    private void searchAmmunition(KnightAmmunitionManager knight, KnightController knightController) {
        System.out.println(SEARCH_AMMUNITION);
        System.out.println(CHOSE_OPTION);
        int numberOfSearch = knightController.getNumberFromUser(RANGE_ONE, RANGE_TWO);
        TypeOfSort typeOfSearch = TypeOfSort.values()[numberOfSearch - 1];
        if (typeOfSearch == TypeOfSort.COST) {
            System.out.println(INPUT_MINIMUM + COST);
            int minCost = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
            System.out.println(INPUT_MAXIMUM + COST);
            int maxCost = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
            showAmmunitionToScreen(knight.searchByCost(minCost, maxCost));
        } else if (typeOfSearch == TypeOfSort.WEIGHT) {
            System.out.println(INPUT_MINIMUM + WEIGHT);
            int minWeight = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
            System.out.println(INPUT_MAXIMUM + WEIGHT);
            int maxWeight = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
            showAmmunitionToScreen(knight.searchByWeight(minWeight, maxWeight));
        } else {
            System.out.println(AMMUNITION_NOT_FOUND);
        }
    }

    private Sword addSword(KnightController knightController) {
        System.out.println(INPUT_SWORD + COST);
        int cost = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
        System.out.println(INPUT_SWORD + WEIGHT);
        int weight = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
        System.out.println(INPUT_SWORD + DAMAGE);
        int damage = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
        return new Sword(damage, weight, cost);
    }

    private Helmet addHelmet(KnightController knightController) {
        System.out.println(INPUT_HELMET + COST);
        int cost = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
        System.out.println(INPUT_HELMET + WEIGHT);
        int weight = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
        System.out.println(INPUT_HELMET + PROTECTION);
        int protection = knightController.getNumberFromUser(0, Integer.MAX_VALUE);
        return new Helmet(protection, weight, cost);
    }

    private void showAmmunitionToScreen(Ammunition[] ammunition) {
        if (ammunition == null || ammunition.length == 0) {
            System.out.println(AMMUNITION_NOT_FOUND);
        } else {
            for (Ammunition oneAmmunition : ammunition) {
                System.out.println(oneAmmunition);
            }
        }
    }

}
