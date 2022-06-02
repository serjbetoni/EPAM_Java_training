package com.epam.knight.controller;

import com.epam.knight.model.KnightAmmunitionManager;
import com.epam.knight.view.ConsoleView;

/**
 * Launches main menu with {@link KnightController}.
 */
public class KnightApplication {

    public static void main(String[] args) {
        KnightApplication application = new KnightApplication();
        application.start();
    }

    private void start() {
        ConsoleView consoleViewer = new ConsoleView();
        KnightController knightController = new KnightController();
        KnightAmmunitionManager knightAmmunition = new KnightAmmunitionManager(KnightGenerator.generateKnight());
        consoleViewer.showMainMenu(knightAmmunition, knightController);
    }
}
