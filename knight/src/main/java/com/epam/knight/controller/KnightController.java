package com.epam.knight.controller;

import java.util.Scanner;

/**
 * Handles main menu and all operations with knight.
 */
public class KnightController {
    private static final String ERROR_COMMAND_NOT_FOUND = "This command not found!";

    public int getNumberFromUser(int from, int to) {
        int numberFromUser = 0;

        Scanner scannerNumber = new Scanner(System.in);
        do {
            numberFromUser = scannerNumber.nextInt();
            if (numberFromUser < from || numberFromUser > to) {
                System.out.println(ERROR_COMMAND_NOT_FOUND);
            }
        } while (numberFromUser < from || numberFromUser > to);
        return numberFromUser;
    }
}
