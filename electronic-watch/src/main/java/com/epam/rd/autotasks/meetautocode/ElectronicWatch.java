package com.epam.rd.autotasks.meetautocode;

import java.util.Scanner;

public class ElectronicWatch {
    public static final int SEC_PER_DAY = 86400;
    public static final int SEC_PER_HOUR = 3600;
    public static final int SEC_PER_MIN = 60;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int seconds = scanner.nextInt();
        System.out.println(getTimeValue(seconds));
    }

    public static String getTimeValue (int seconds){
        return String.format("%2d:%02d:%02d",
                seconds % SEC_PER_DAY / SEC_PER_HOUR,
                seconds % SEC_PER_HOUR / SEC_PER_MIN,
                seconds % SEC_PER_MIN);
    }
}
