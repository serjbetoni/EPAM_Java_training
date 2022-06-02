package com.epam.rd.autotasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Average {
    public static final int END_NUM = 1;
    public static final int ZERO_RESULT = 0;

    public static void main(String[] args) {
        System.out.println(getAverageOfNumbers(getSequenceOfNumbers()));
    }

    public static List<Integer> getSequenceOfNumbers(){
        Scanner scanner = new Scanner(System.in);
        int num;
        List<Integer> numSequence = new ArrayList<>();
        do {
            num = scanner.nextInt();
            numSequence.add(num);
        } while (num != ZERO_RESULT);
        return numSequence;
    }

    public static int getAverageOfNumbers(List<Integer> numSequence) {
        int sum = 0;
        int result;
        for (int i : numSequence) {
            sum = sum + i;
        }
        if (numSequence.size() > 1){
            result = sum / (numSequence.size() - END_NUM);
            return result;
        } else {
            return ZERO_RESULT;
        }
    }
}
